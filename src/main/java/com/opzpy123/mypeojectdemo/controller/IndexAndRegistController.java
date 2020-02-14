package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.util.TransformTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 注册登录页面访问uri
 */
@Controller
public class IndexAndRegistController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    /**
     * 指定/index到index.html
     *
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookie_user")) {
                    String cookie_user = TransformTest.hexStr2Str(cookie.getValue());
                    User user = userService.findUserByName(cookie_user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    break;
                }
            }
        }
        List<Question> questions = questionService.selectAllQuestion();
        model.addAttribute("resultList", questions);

        return "index";
    }


    /**
     * 访问注册页面
     *
     * @return
     */
    @GetMapping("/userRegist")
    public String regist() {
        return "userRegist";
    }


}
