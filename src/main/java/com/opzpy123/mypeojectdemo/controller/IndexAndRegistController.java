package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 注册登录页面访问uri
 */
@Controller
public class IndexAndRegistController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    /**
     * 指定/index到index.html
     *
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        if(request.getCookies()!=null){
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie:cookies){
                System.out.println(cookie.getName()+":{"+cookie.getValue()+"};");
                if(cookie.getName().equals("cookie_user")){
                    String cookie_user = cookie.getValue();
                    User user = userMapper.findUserByName(cookie_user);
                    userService.login(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                    break;
                }
            }
        }




        return "index";
    }

    /**
     * 访问注册页面
     * @return
     */
    @GetMapping("/userRegist")
    public String regist() {
        return "userRegist";
    }
}
