package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.User;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据name去数据库中查找有无这个user
     * @param username
     * @return
     */
    User findUserByName(String username);

    /**
     * 注册
     * @param user
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void regist(User user);

    /**
     * 登录
     *
     * @param user
     * @return
     */
    Long login(User user);

}
