package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.bean.Notification;
import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.NotificationDTO;
import com.opzpy123.mypeojectdemo.dto.PaginationDTO;
import com.opzpy123.mypeojectdemo.dto.QuestionDTO;
import com.opzpy123.mypeojectdemo.mapper.CommentMapper;
import com.opzpy123.mypeojectdemo.mapper.NotificationMapper;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;


    public void insert(Notification notification) {
        notificationMapper.insert(notification);
    }


    public List<NotificationDTO> selectQuestionDTO(Long userId) {

        List<Notification> notifications = notificationMapper.selectUserQuestion(userId);
        if (notifications.size() == 0) {
            return null;
        }
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        notifications.forEach(i -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            User user = userMapper.findUserById(i.getNotifier());
            notificationDTO.setId(i.getId());
            notificationDTO.setGmtCreate(i.getGmtCreate());
            notificationDTO.setNotifier(user);
            notificationDTO.setStatus(i.getStatus());
            notificationDTO.setContent(i.getCommentContent());
            notificationDTO.setType(i.getType());

            if (i.getType() == 1) {
                //一级回复
                Question question = questionMapper.selectById(i.getOuterId());
                notificationDTO.setOuterId(i.getOuterId());
                //如果问题不存在
                if (question != null) {
                    notificationDTO.setOuterTitle(question.getTitle());
                    notificationDTOList.add(notificationDTO);
                }
            } else {
                //二级回复的情况下
                notificationDTO.setOuterTitle("我");
                Comment comment = commentMapper.selectById(i.getOuterId());
                //如果回复不存在
                if (comment != null) {
                    notificationDTO.setOuterId(comment.getParentId());
                    notificationDTOList.add(notificationDTO);
                }
            }
        });

        return notificationDTOList;
    }

    public Long unreadCount(Long id) {
        return notificationMapper.unreadCount(id);
    }

    public Notification selectById(Long id){
        return notificationMapper.selectById(id);
    }

    public void changeStatus(Long id) {
        notificationMapper.changeStatus(id);
    }
}
