package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import com.opzpy123.mypeojectdemo.util.AvatarGenerater;
import com.opzpy123.mypeojectdemo.util.Result;
import com.opzpy123.mypeojectdemo.util.TransformTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService{
	@Autowired
	private UserMapper userMapper;

	/**
	 * 注册
	 *
	 * @param user 参数封装
	 * @return Result
	 */
	@CacheEvict(allEntries = true)
	public Result regist(User user) {
		Result result = new Result();
		result.setSuccess(false);
		result.setDetail(null);
		try {
			User existUser = userMapper.findUserByName(user.getUsername());
			if (existUser != null) {
				//如果用户名已存在
				result.setMsg("用户名已存在");

			} else {
				user.setAvatarUrl("image/letterAvatar/" + user.getUsername().charAt(0) + ".jpg");
				userMapper.regist(user);
				result.setMsg("注册成功");
				result.setSuccess(true);
				result.setDetail(user);
			}
		} catch (Exception e) {
			result.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 登录
	 *
	 * @param user 用户名和密码
	 * @return Result
	 */
	public Result login(User user) {
		Result result = new Result();
		result.setSuccess(false);
		result.setDetail(null);
		try {
			Long userId = userMapper.login(user);
			if (userId == null) {
				result.setMsg("用户名或密码错误");
			} else {
				result.setMsg("登录成功");
				result.setSuccess(true);
				user.setId(userId);
				result.setDetail(user);
			}
		} catch (Exception e) {
			result.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Cacheable(value="users", key="#username")
	public User findUserByName(String username) {
		return userMapper.findUserByName(username);
	}


	@Cacheable(value="users", key="#id")
	public User findUserById(Long id) {
		return userMapper.findUserById(id);
	}

	@Cacheable(value="users")
	public List<User> list(){
		return userMapper.list();
	}

	@CacheEvict(allEntries = true)
	public boolean updateUserContact(Long id, String contact) {
		User userById = userMapper.findUserById(id);
		if (userById == null) {
			return false;
		}
		return userMapper.updateUserContact(id, contact);
	}

	public User getCurrentUser(HttpServletRequest request){
		User user=null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cookie_user")) {
					String cookie_user = TransformTest.hexStr2Str(cookie.getValue());
					user=findUserByName(cookie_user);
					break;
				}
			}
		}
		if(user==null)throw new RuntimeException("用户登录状态异常");
		return user;
	}

}
