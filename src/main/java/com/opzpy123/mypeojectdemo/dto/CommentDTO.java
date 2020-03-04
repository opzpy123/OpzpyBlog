package com.opzpy123.mypeojectdemo.dto;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
