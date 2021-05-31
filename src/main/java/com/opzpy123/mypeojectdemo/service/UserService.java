package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import com.opzpy123.mypeojectdemo.util.AvatarGenerater;
import com.opzpy123.mypeojectdemo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 注册
	 *
	 * @param user 参数封装
	 * @return Result
	 */
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


	public User findUserByName(String username) {
		return userMapper.findUserByName(username);
	}

	public User findUserById(Long id) {
		return userMapper.findUserById(id);
	}

	public boolean updateUserContact(Long id, String contact) {

		User userById = userMapper.findUserById(id);
		if (userById == null) {
			return false;
		}
		boolean isSuccess = userMapper.updateUserContact(id, contact);
		return isSuccess;
	}

}
