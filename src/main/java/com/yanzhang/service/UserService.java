package com.yanzhang.service;

import com.yanzhang.pojo.User;

public interface UserService {

    User userSimpleInfo(String name);

    User userPermissionInfo(String name);
}
