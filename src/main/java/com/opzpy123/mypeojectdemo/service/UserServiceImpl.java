package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.mapper.UserMapper;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    //@Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }


}
