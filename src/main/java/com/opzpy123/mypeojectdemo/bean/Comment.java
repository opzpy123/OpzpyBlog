package com.opzpy123.mypeojectdemo.bean;

import lombok.Data;

/**
 * 回复
 */

@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Long commentCount;
}
