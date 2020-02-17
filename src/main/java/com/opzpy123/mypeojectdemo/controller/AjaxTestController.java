package com.opzpy123.mypeojectdemo.controller;

import com.alibaba.fastjson.JSON;
import com.opzpy123.mypeojectdemo.bean.AjaxUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AjaxTestController {

    @GetMapping("/ajaxHtml")
    public String ajaxHtml(){
        return "AjaxTestHtml";
    }

    @PostMapping("/yourUrl")
    @ResponseBody
    public AjaxUser userData(AjaxUser user) {
        System.out.println("username=" + user.getUsername() + ";userid=" + user.getUserid()+user.getInputval());
        return user;
    }

}
