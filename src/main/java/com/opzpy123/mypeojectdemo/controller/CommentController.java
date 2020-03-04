package com.opzpy123.mypeojectdemo.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.CommentCreateDTO;
import com.opzpy123.mypeojectdemo.dto.LikeCountDTO;
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
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){


        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO.getContent()==null|| commentCreateDTO.getContent()==""){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_NULL);
        }
        Comment comment = new Comment();
        //前端ajax传来的数据自动封装进commentDTO中;
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());

        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);

        return ResultDTO.okOf();
    }

    @PostMapping("/likeCount")
    @ResponseBody
    public Object likeCount(@RequestBody LikeCountDTO likeCountDTO){
        commentService.likeCount(likeCountDTO);

        return likeCountDTO;
    }


}
