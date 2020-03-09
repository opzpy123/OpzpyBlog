package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.dto.CommentDTO;
import com.opzpy123.mypeojectdemo.dto.QuestionDTO;
import com.opzpy123.mypeojectdemo.enums.CommentTypeEnum;
import com.opzpy123.mypeojectdemo.service.CommentService;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {

        QuestionDTO question = questionService.selectById(id);

        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION.getType());
        model.addAttribute("comments",commentDTOS);

        question.setViewCount(questionService.incView(id));
        model.addAttribute("question", question);
        return "question";
    }


}
