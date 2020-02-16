package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;


    //后端数据校验
    public String create(Question question, Model model){
        model.addAttribute("returnTitle",question.getTitle());
        model.addAttribute("returnDescription",question.getDescription());
        model.addAttribute("returnTag",question.getTag());

        if(question.getTitle().isEmpty()){
            return "标题不能为空";
        }
        if(question.getTitle().length()>30){
            return "标题过长";
        }
        if(question.getDescription().isEmpty()) {
            return "描述不能为空";
        }
            questionMapper.create(question);
            return "";
    }

    public List<Question> selectAllQuestion(){
        List<Question> questions = questionMapper.selectAllQuestion();
        return questions;
    }
}
