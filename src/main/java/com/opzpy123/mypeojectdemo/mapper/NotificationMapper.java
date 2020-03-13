package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.Comment;
import com.opzpy123.mypeojectdemo.bean.Notification;
import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.enums.NotificationTypeEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(Notification notification);

    Integer count();

    List<Notification> selectUserQuestion(Long userId);

    Long unreadCount(Long id);


    void deleteByTypeAndOuterId(Long id, int type);

    Notification selectById(Long id);

    void changeStatus(Long id);
}
