package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.NotificationDTO;
import com.opzpy123.mypeojectdemo.dto.PaginationDTO;
import com.opzpy123.mypeojectdemo.service.NotificationService;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if (("questions".equals(action))) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的博客");
            //没用分页，后两个参数暂时无效
            PaginationDTO paginationDTO = questionService.selectQuestionDTO(user.getId(), page, size);
            model.addAttribute("resultList", paginationDTO);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");

            List<NotificationDTO> notificationDTOList =notificationService.selectQuestionDTO(user.getId());
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("unreadCount", unreadCount);
            System.out.println(notificationDTOList);
            model.addAttribute("notificationDTOList", notificationDTOList);
        }

        return "profile";
    }

}
