package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    void create(Question question);

    List<Question> selectAllQuestion(Integer offset, Integer size);

    Integer count();

    List<Question> selectUserQuestion(Long userId, Integer offset, Integer size);

    Question selectById(Long id);

    void update(Question question);

    void delete(Long id);

    void incView(Long id, Integer viewCount);

    void incCommentCount(Long id, Integer commentCount);

    List<Question> selectRelated(Question question);

    List<Question> selectBySearch(String search);

    List<Question> selectByCommentCount();
}
