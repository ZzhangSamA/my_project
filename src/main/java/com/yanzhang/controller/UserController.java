package com.yanzhang.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/add")
    @RequiresPermissions({"user:add"}) //需要权限
    public Object add() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",1);
        map.put("msg","调用用户添加成功");

        return map;
    }

}
