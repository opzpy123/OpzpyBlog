package com.opzpy123.mypeojectdemo.dto.pubsub;

import com.alibaba.fastjson.JSONObject;
import com.opzpy123.mypeojectdemo.dto.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class RedisMessageSubscriber implements MessageListener {

    public static HashMap<String, List<MessageVo>> messageMap = new HashMap<>();


    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        String s = message.toString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                s = (String) s.subSequence(i + 1, s.length() - 1);
                break;
            }
        }
        s="{"+s+"}";
        MessageVo messageVo = JSONObject.parseObject(s, MessageVo.class);
        //收到消息时 按名字存放到keyEntry每个人的List中
        //进入聊天室 则将自己的名字注册进messageMap中
        //推出聊天室 则剔除messageMap
        for (String key : messageMap.keySet()) {
            List<MessageVo> messageVos = messageMap.get(key);
            messageVos.add(messageVo);
        }
    }
}
