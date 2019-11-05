package com.yanzhang.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @RequestMapping("/add")
    @RequiresPermissions({"video:add"})
    public  Object add() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",1);
        map.put("msg","视频添加成功");

        return map;
    }
}
