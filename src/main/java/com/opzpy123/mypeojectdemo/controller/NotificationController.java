package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.bean.Notification;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.CommentService;
import com.opzpy123.mypeojectdemo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String NotificationRead(@PathVariable(value="id")Long id, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
         if(user==null){
             return "redirect:/";
         }
        Notification notification = notificationService.selectById(id);
        System.out.println(notification);
        notification.setStatus(1);
        notificationService.changeStatus(id);
        if(notification.getType()==1){
            return "redirect:/question/"+notification.getOuterId();
        }else if(notification.getType()==2){
            Comment comment = commentService.selectById(notification.getOuterId());
            return "redirect:/question/"+ comment.getParentId();
        }else {
            return "redirect:/";
        }


    }

}
