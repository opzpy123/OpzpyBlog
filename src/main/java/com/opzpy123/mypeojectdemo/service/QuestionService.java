package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.PaginationDTO;
import com.opzpy123.mypeojectdemo.dto.QuestionDTO;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserService userService;


    //后端数据校验
    public String create(Question question, Model model) {
        //校验数据回显
        model.addAttribute("returnTitle", question.getTitle());
        model.addAttribute("returnDescription", question.getDescription());
        model.addAttribute("returnTag", question.getTag());

        if (question.getTitle().isEmpty()) {
            return "标题不能为空";
        }
        if (question.getTitle().length() > 30) {
            return "标题过长";
        }
        if (question.getDescription().isEmpty()) {
            return "描述不能为空";
        }
        questionMapper.create(question);
        return "";
    }

    /**
     * 拿到所有的问题
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO selectQuestionDTO(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectAllQuestion(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        questions.forEach(i -> {
            User user = userService.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setQuestionDTOS(questionDTOList);
        return paginationDTO;

    }

    /**
     * 根据传入的user拿到当前用户发表的question
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO selectQuestionDTO(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectUserQuestion(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        questions.forEach(i -> {
            User user = userService.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setQuestionDTOS(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO selectById(Integer id) {
       Question question = questionMapper.selectById(id);
        User user = userService.findUserById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(user);
       BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;

    }
}
