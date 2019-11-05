package com.yanzhang.mapper;

import com.yanzhang.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<User> {

    User userSimpleInfo(String name);

    User userPermissionInfo(String name);
}
