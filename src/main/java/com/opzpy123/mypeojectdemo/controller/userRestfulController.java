package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.util.Result;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * restfulController
 */
@Controller
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
    public String regist(User user) {
       if(userService.regist(user).isSuccess()){
           return "redirect:/";
       }else {
           return "redirect:/";
       }

    }

    /**
     * 登录
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public String login(User user, HttpServletRequest request) {
        if(userService.login(user).isSuccess()){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/outLogging")
    public String outLogging(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }


}
