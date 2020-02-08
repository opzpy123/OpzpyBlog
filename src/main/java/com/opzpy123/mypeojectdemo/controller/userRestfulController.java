package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.util.Result;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * restfulController
 */
@RestController
@RequestMapping("/user")
public class userRestfulController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/regist")
    public Result regist(User user) {
        return userService.regist(user);
    }

    /**
     * 登录
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(User user) {
        return userService.login(user);
    }


}
