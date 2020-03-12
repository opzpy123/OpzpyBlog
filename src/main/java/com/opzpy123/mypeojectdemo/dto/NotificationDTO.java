package com.opzpy123.mypeojectdemo.dto;

import com.opzpy123.mypeojectdemo.bean.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private Long outerId;//问题id
    private String outerTitle;//问题标题
    private Integer type;
    private String content;

}
