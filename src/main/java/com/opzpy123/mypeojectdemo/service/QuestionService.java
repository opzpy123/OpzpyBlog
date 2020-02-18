package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.QuestionDTO;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserService userService;


    //后端数据校验
    public String create(Question question, Model model){
        //校验数据回显
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
        //集合倒叙排列
        Collections.reverse(questions);
        return questions;
    }


    //把question和user在后台做关联查询
    public List<QuestionDTO> selectQuestionDTO(){
        List<Question> questions = questionMapper.selectAllQuestion();
        List<QuestionDTO> questionDTOS =new ArrayList<>();
        questions.forEach(i->{
            User user = userService.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        });
        Collections.reverse(questionDTOS);
        return questionDTOS;

    }
}
