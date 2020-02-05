package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService {

    List<User> selectAllUser();

}
