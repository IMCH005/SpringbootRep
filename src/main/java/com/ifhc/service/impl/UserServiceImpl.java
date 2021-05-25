package com.ifhc.service.impl;

import com.ifhc.entity.User;
import com.ifhc.mapper.UserMapper;
import com.ifhc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }
}
