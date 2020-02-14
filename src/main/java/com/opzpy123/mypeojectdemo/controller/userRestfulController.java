package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.util.Result;
import com.opzpy123.mypeojectdemo.util.TransformTest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
    public String regist(User user, Model model, HttpServletResponse response, HttpServletRequest request) {
        if (userService.regist(user).isSuccess()) {
            this.login(user,request,response);
            return "redirect:/";
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String registMsg = userService.regist(user).getMsg();
            out.print("<script>alert('" + registMsg + "');window.location.href='"
                    + "/userRegist" + "';</script>");

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
        return "index";


    }

    /**
     * 登录
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response) {
        if (userService.login(user).isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            Cookie cookie = new Cookie("cookie_user", TransformTest.str2HexStr(user.getUsername()));
            cookie.setMaxAge(60 * 60 * 24 * 3);//3天免登录
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                String loginMsg = userService.login(user).getMsg();
                out.print("<script>alert('" + loginMsg + "');window.location.href='"
                        + "/" + "';</script>");

                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(out);
            }
            return "index";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/outLogging")
    public String outLogging(HttpServletRequest request, HttpServletResponse response) {
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
                    break;
                }
            }
        }


        return "redirect:/";
    }
}

