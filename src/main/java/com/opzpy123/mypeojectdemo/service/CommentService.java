package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.CommentDTO;
import com.opzpy123.mypeojectdemo.enums.CommentTypeEnum;
import com.opzpy123.mypeojectdemo.exception.CustomizeErrorCode;
import com.opzpy123.mypeojectdemo.exception.CustomizeException;
import com.opzpy123.mypeojectdemo.mapper.CommentMapper;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

   @Autowired
    private UserMapper userMapper;



    public void insert(Comment comment) {
        if(comment.getParentId()==null || comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOTFOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复问题
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOTFOUND);
            }
            commentMapper.insert(comment);
            commentMapper.incCommentCount(comment.getParentId(),1);
        }else {
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question.getId(),question.getCommentCount());
        }
    }

    public List<CommentDTO> listByTargetId(Long id, Integer type) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        List<Comment> comments = commentMapper.selectByParentId(id,type);
        if(comments==null){
            return new ArrayList<>();
        }
        comments.forEach(i->{
            CommentDTO commentDTO = new CommentDTO();
            User user = userMapper.findUserById(i.getCommentator());
            commentDTO.setUser(user);
            BeanUtils.copyProperties(i,commentDTO);
            commentDTOS.add(commentDTO);
        });
        return commentDTOS;
    }


    public void incLikeCount(Long id) {
        commentMapper.incLikeCount(id,1);
    }
}
