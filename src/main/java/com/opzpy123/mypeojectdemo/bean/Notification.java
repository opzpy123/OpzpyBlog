package com.opzpy123.mypeojectdemo.bean;

import lombok.Data;

@Data
public class Notification {
    private Long id;
    private Long notifier;//回复者id
    private Long receiver;//被回复者id
    private Long outerId;//questionId
    private Integer type;//判断是回复了问题还是回复了评论
    private Long gmtCreate;
    private Integer status;//判断是否已读
    private String commentContent;//消息内容


}
