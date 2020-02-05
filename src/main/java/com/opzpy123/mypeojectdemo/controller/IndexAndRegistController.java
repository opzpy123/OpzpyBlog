package com.opzpy123.mypeojectdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexAndRegistController {
        @GetMapping("/index")
    public String index(){
            return "index";
        }

        @GetMapping("/userRegist")
    public String regist(){
            return "userRegist";
        }
}
