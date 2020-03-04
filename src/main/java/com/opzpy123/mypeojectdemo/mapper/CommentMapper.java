package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {


    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(Comment comment);

    Comment selectById(Long id);

    List<Comment> selectByParentId(Long id);

    void likeCount(Integer commentId, Integer likeCount);
}
