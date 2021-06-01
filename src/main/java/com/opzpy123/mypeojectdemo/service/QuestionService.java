package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.exception.CustomizeErrorCode;
import com.opzpy123.mypeojectdemo.exception.CustomizeException;
import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.PaginationDTO;
import com.opzpy123.mypeojectdemo.dto.QuestionDTO;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public void edit(Long id, Model model) {
        Question question = questionMapper.selectById(id);
        //校验数据回显
        model.addAttribute("returnTitle", question.getTitle());
        model.addAttribute("returnDescription", question.getDescription());
        model.addAttribute("returnTag", question.getTag());
        model.addAttribute("id", question.getId());

    }

    public String createOrUpdate(Question question, Model model) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            //校验数据回显
            model.addAttribute("returnTitle", question.getTitle());
            model.addAttribute("returnDescription", question.getDescription());
            model.addAttribute("returnTag", question.getTag());
            model.addAttribute("id", question.getId());

            if (question.getTitle().isEmpty()) {
                return "标题不能为空";
            } else if (question.getTitle().length() > 30) {
                return "标题过长";
            } else if (question.getDescription().isEmpty()) {
                return "描述不能为空";
            } else {
                questionMapper.create(question);
                return "";
            }
        } else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            //校验数据回显
            model.addAttribute("returnTitle", question.getTitle());
            model.addAttribute("returnDescription", question.getDescription());
            model.addAttribute("returnTag", question.getTag());
            model.addAttribute("id", question.getId());

            if (question.getTitle().isEmpty()) {
                return "标题不能为空";
            } else if (question.getTitle().length() > 30) {
                return "标题过长";
            } else if (question.getDescription().isEmpty()) {
                return "描述不能为空";
            } else {
                //业务场景:处理用户的编辑中问题被删除的情况
                Question question1 = questionMapper.selectById(question.getId());
                if (question1 == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);
                } else {
                    questionMapper.update(question);
                }
                return "";
            }
        }

    }

    /**
     * 拿到所有的问题
     *
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
        if (page >= paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectAllQuestion(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        questions.forEach(i -> {
            User user = userMapper.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setData(questionDTOList);
        return paginationDTO;

    }

    /**
     * 根据传入的user拿到当前用户发表的question
     *
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
        List<Question> questions = questionMapper.selectUserQuestion(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        questions.forEach(i -> {
            User user = userMapper.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO selectById(Long id) {
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOTFOUND);
        }
        User user = userMapper.findUserById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question, questionDTO);
        return questionDTO;

    }


    public void delete(Long id) {
        questionMapper.delete(id);
    }

    public int incView(Long id) {
        Question question = questionMapper.selectById(id);
        questionMapper.incView(id, question.getViewCount());
        return question.getViewCount() + 1;
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(questionDTO.getTag().replace(',', '|'));


        List<Question> questions = questionMapper.selectRelated(question);

        //dto数据 流转换
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        questions.forEach(i -> {
            QuestionDTO questionDTOTemp = new QuestionDTO();
            User user = userMapper.findUserById(i.getCreator());
            BeanUtils.copyProperties(i, questionDTOTemp);
            questionDTOTemp.setUser(user);
            questionDTOS.add(questionDTOTemp);
        });
        return questionDTOS;


    }

    public PaginationDTO selectBySearch(String search) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        String searchParam = "%" + search + "%";
        paginationDTO.setPagination(1, 1, 1);
        List<Question> questions;
        User  TempUser = userMapper.findUserByName(search) ;
        if(TempUser!=null){
            questions = questionMapper.selectUserQuestion(TempUser.getId(), 1, 1);
            questions.forEach(i -> {
                User user = userMapper.findUserById(i.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(i, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            });
        }
        questions = questionMapper.selectBySearch(searchParam);
        questions.forEach(i -> {
            User user = userMapper.findUserById(i.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(i, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public List<Question> selectByCommentCount() {
        return questionMapper.selectByCommentCount();
    }
}
