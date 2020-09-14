package com.yanzhang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pub")
public class PublicController {

    @RequestMapping("/add")
    public Object add() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",1);
        map.put("msg","公用资源调用 add 成功");

        return map;
    }

    @RequestMapping("/resource/list")
    public Object resourceList() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",1);
        map.put("msg","公用资源调用 resourceList 成功");

        return map;
    }

}
