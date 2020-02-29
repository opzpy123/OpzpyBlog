package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;


    @GetMapping("/publish")
    public String publish(HttpServletRequest request, Model model) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "登录之后才可以发博客");
            return "publish";
        }

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model,
            HttpServletResponse response
    ) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "登录之后才可以发博客");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        String s = questionService.create(question,model);
        model.addAttribute("publishMsg", s);
        if (s.isEmpty()) {
            return "redirect:/";
        } else {
            return "publish";
        }
    }

}
