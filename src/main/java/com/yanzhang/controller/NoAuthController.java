package com.yanzhang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/no_auth")
public class NoAuthController {

    @RequestMapping
    public Object noAuth() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",-3);
        map.put("msg","您没有访问权限");

        return map;
    }
}
