package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.ApiResponse;
import com.opzpy123.mypeojectdemo.dto.MessageVo;
import com.opzpy123.mypeojectdemo.dto.pubsub.RedisMessagePublisher;
import com.opzpy123.mypeojectdemo.dto.pubsub.RedisMessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TalkService {

    @Resource
    private RedisMessagePublisher redisMessagePublisher;

    @Resource
    private UserService userService;

    public ApiResponse<String> enter(User principal) {
        log.info(principal.getUsername() + "进入房间");
        //需要把自己注册进map
        RedisMessageSubscriber.messageMap.putIfAbsent(principal.getUsername(), new ArrayList<>());

        MessageVo messageVo = new MessageVo();
        messageVo.setMessage(principal.getUsername() + "-进入房间");
        messageVo.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        messageVo.setUserName(principal.getUsername());

        redisMessagePublisher.publish(messageVo);

        return ApiResponse.ofSuccess();
    }

    public ApiResponse<String> sendMessage(User principal, String message) {
        MessageVo messageVo = new MessageVo();
        messageVo.setMessage(message);
        messageVo.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        messageVo.setUserName(principal.getUsername());

        redisMessagePublisher.publish(messageVo);
        log.info(principal.getUsername() + "发送消息" + message);
        return ApiResponse.ofSuccess();
    }

    private final HashMap<String, String> tempTimeMap = new HashMap<String, String>();

    public ApiResponse<List<MessageVo>> getMessage(User principal,String talkUserId) {

        List<MessageVo> res = new ArrayList<>();
        if (RedisMessageSubscriber.messageMap.get(principal.getUsername()) != null)
            res = RedisMessageSubscriber.messageMap.get(principal.getUsername());
        //只显示过去150条消息
        User talkUserById = userService.findUserById(Long.valueOf(talkUserId));
        if (res.size() >= 150)
            res = res.stream()
                    .filter(a-> a.getUserName().equals(principal.getUsername()) ||a.getUserName().equals(talkUserById.getUsername()))
                    .skip(res.size() - 150).collect(Collectors.toList());
        //展示逻辑 未更新返回[] 已更新则返回最近150条
        tempTimeMap.putIfAbsent(principal.getUsername(), null);
        String tempTime = tempTimeMap.get(principal.getUsername());
        if (res.size() > 0) {
            if (tempTime == null) {
                tempTime = res.get(res.size() - 1).getSendTime();
                tempTimeMap.put(principal.getUsername(), tempTime);
            } else if (tempTime.equals(res.get(res.size() - 1).getSendTime())) {
                res = new ArrayList<>();
            } else {
                tempTime = res.get(res.size() - 1).getSendTime();
                tempTimeMap.put(principal.getUsername(), tempTime);
            }
        } else {
            tempTime = null;
            tempTimeMap.put(principal.getUsername(), tempTime);
            res = new ArrayList<>();
        }
        return ApiResponse.ofSuccess(res);
    }

    public ApiResponse<String> exit(User principal) {
        log.info(principal.getUsername() + "退出房间");
        //退出房间不清理消息
        //RedisMessageSubscriber.messageMap.remove(principal.getName());

        MessageVo messageVo = new MessageVo();
        messageVo.setMessage(principal.getUsername() + "已退出聊天");
        messageVo.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        messageVo.setUserName(principal.getUsername());

        redisMessagePublisher.publish(messageVo);

        return ApiResponse.ofSuccess();
    }
}
