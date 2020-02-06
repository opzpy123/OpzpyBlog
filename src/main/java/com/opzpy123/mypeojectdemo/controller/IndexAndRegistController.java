package com.opzpy123.mypeojectdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册登录页面访问uri
 */
@Controller
public class IndexAndRegistController {
    /**
     * 指定/index到index.html
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 访问注册页面
     * @return
     */
    @GetMapping("/userRegist")
    public String regist() {
        return "userRegist";
    }
}
