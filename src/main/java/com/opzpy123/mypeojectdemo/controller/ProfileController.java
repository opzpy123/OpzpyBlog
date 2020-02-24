package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.PaginationDTO;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.util.TransformTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = null;
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cookie_user")) {
                    String cookie_user = TransformTest.hexStr2Str(cookie.getValue());
                    user = userService.findUserByName(cookie_user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    break;
                }
            }
        }
        if (user == null) {
            return "redirect:/";
        }

        if (("questions".equals(action))) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        user.getId();
        PaginationDTO paginationDTO = questionService.selectQuestionDTO(user.getId(), page, size);
        model.addAttribute("resultList", paginationDTO);
        return "profile";
    }

//    @GetMapping("/profile")
//    public String profileIndex(){
//        return "profile";
//    }
}
