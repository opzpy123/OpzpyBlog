package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.bean.Result;
import com.opzpy123.mypeojectdemo.util.PageAlert;
import com.opzpy123.mypeojectdemo.util.TransformTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public String regist(User user, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Result regist = userService.regist(user);
        String registMsg = regist.getMsg();
        if (regist.isSuccess()) {
            String returnMsg = "<script>alert('" + registMsg + "');window.location.href='" + "/" + "';</script>";
          this.login(user, request, response, model);
            PageAlert.Alert(returnMsg, response);
            return "redirect:/";
        } else {
            String returnMsg = "<script>alert('" + registMsg + "');window.location.href='" + "/userRegist" + "';</script>";
            PageAlert.Alert(returnMsg, response);
            return "redirect:/userRegist";
        }


    }

    /**
     * 登录
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        HttpSession session = request.getSession();
        Result login = userService.login(user);
        String loginMsg = login.getMsg();
        if(login.isSuccess()) {
            session.setAttribute("user", user);
            Cookie cookie = new Cookie("cookie_user", TransformTest.str2HexStr(user.getUsername()));
            cookie.setMaxAge(60 * 60 * 24 * 3);//3天免登录
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        String returnMsg = "<script>alert('" + loginMsg + "');window.location.href='" + "/" + "';</script>";
             PageAlert.Alert(returnMsg, response);

        return "redirect:/";
    }



    /**
     * 退出登录
     */
    @GetMapping("/outLogging")
    public String outLogging(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookie_user")) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    String returnMsg = "<script>alert('" + "用户已退出登录" + "');window.location.href='" + "/" + "';</script>";
                     PageAlert.Alert(returnMsg, response);
                    break;
                }
            }
        }

        return "redirect:/";
    }

}

