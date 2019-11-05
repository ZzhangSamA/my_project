package com.yanzhang.service.impl;

import com.yanzhang.mapper.UserMapper;
import com.yanzhang.pojo.User;
import com.yanzhang.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User userSimpleInfo(String name) {
        return userMapper.userSimpleInfo(name);
    }

    @Override
    public User userPermissionInfo(String name) {
        return userMapper.userPermissionInfo(name);
    }
}
