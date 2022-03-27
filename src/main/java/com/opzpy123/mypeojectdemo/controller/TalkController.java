package com.opzpy123.mypeojectdemo.controller;


import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.ApiResponse;
import com.opzpy123.mypeojectdemo.dto.MessageVo;
import com.opzpy123.mypeojectdemo.service.TalkService;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/talk")
public class TalkController {

    @Resource
    private TalkService talkService;
    @Resource
    private UserService userService;

    @GetMapping("/page")
    public String talkPage(Model model, HttpServletRequest request,@RequestParam String userId) {
        User currentUser = userService.getCurrentUser(request);
        User userById = userService.findUserById(Long.valueOf(userId));
        model.addAttribute("loginUser", currentUser);
        model.addAttribute("talkUserId",userId);
        model.addAttribute("talkUserContact",userById.getContact());
        return "talk";
    }


    /**
     * 进入房间，将自己注册进消息map 广播自己进入房间的消息
     */
    @GetMapping("/enter")
    @ResponseBody
    ApiResponse<String> enter(HttpServletRequest request) {
        User principal = userService.getCurrentUser(request);
        return talkService.enter(principal);
    }

    /**
     * 指定发送内容的广播消息
     */
    @GetMapping("/send")
    @ResponseBody
    ApiResponse<String> sendMessage(HttpServletRequest request, String message) {
        User principal = userService.getCurrentUser(request);
        return talkService.sendMessage(principal, message);
    }

    /**
     * 接受自己的消息
     */
    @GetMapping("/receive")
    @ResponseBody
    ApiResponse<List<MessageVo>> getMessage(HttpServletRequest request, @RequestParam String talkUserId) {
        User principal = userService.getCurrentUser(request);
        ApiResponse<List<MessageVo>> message = talkService.getMessage(principal,talkUserId);
        return message;
    }

    /**
     * 退出房间，广播退出时的消息 清理消息map
     */
    @GetMapping("/exit")
    @ResponseBody
    ApiResponse<String> exit(HttpServletRequest request) {
        User principal = userService.getCurrentUser(request);
        return talkService.exit(principal);
    }
}
