package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.PaginationDTO;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import com.opzpy123.mypeojectdemo.service.NotificationService;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 注册登录页面访问uri
 */
@Controller
public class IndexAndRegistController {

    @Autowired
    private UserMapper userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;


    /**
     * 指定/index到index.html
     *
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        HttpServletResponse response,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {



        PaginationDTO paginationDTOs = questionService.selectQuestionDTO(page,size);
        model.addAttribute("resultList", paginationDTOs);
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
