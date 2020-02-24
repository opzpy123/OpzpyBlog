package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

     void create(Question question);

     List<Question> selectAllQuestion(Integer offset,Integer size);


     Integer count();


     List<Question> selectUserQuestion(Long userId, Integer offset, Integer size);
}
