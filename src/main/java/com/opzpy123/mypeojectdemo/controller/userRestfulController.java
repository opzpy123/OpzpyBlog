package com.opzpy123.mypeojectdemo.controller;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class userRestfulController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> selectAllUser(){
        return userService.selectAllUser();
    }



}
