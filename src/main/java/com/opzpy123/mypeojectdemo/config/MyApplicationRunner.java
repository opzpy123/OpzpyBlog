package com.opzpy123.mypeojectdemo.config;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.pubsub.RedisMessageSubscriber;
import com.opzpy123.mypeojectdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 定时任务启动
 */
@Slf4j
@Configuration
public class MyApplicationRunner implements ApplicationRunner {

    @Resource
    private UserService userService;
    @Override
    public void run(ApplicationArguments args) {
        //启动时所有人置于聊天室
        for (User authUser : userService.list()) {
            RedisMessageSubscriber.messageMap.putIfAbsent(authUser.getUsername(), new ArrayList<>());
        }
    }
}
