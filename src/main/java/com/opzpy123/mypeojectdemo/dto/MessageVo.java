package com.opzpy123.mypeojectdemo.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 用户redis发送消息的vo
 */
@Data
@Slf4j
public class MessageVo implements Serializable {

    private String message;
    private String userName;
    private String sendTime;
}
