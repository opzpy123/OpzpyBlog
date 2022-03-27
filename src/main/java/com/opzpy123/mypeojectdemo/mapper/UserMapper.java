package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.User;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserMapper {

     User findUserByName(String username);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void regist(User user);

    Long login(User user);

    User findUserById(Long id);

    List<User> list();

    Boolean updateUserContact(Long id, String contact);


}
