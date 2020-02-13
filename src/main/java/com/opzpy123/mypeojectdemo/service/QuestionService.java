package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;


    public void create(Question question){
        questionMapper.create(question);
    }

    public List<Question> selectAllQuestion(){
        List<Question> questions = questionMapper.selectAllQuestion();
        return questions;
    }
}
