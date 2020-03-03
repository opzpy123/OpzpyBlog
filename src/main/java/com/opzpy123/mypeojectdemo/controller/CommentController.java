package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.CommentDTO;
import com.opzpy123.mypeojectdemo.dto.ResultDTO;
import com.opzpy123.mypeojectdemo.exception.CustomizeErrorCode;
import com.opzpy123.mypeojectdemo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO,HttpServletRequest request){


        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        //前端ajax传来的数据自动封装进commentDTO中;
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());

        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);

        return ResultDTO.okOf();
    }

}
