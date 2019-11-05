package com.yanzhang.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 针对controller抛出异常后，一个处理异常的通知
 */
@ControllerAdvice
public class AuthorizationExceptionController {

    /**
     * 处理在controller中抛出该异常后的处理方法
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Object authorizationExceptionHandler() {

        Map<String, Object> map = new HashMap<>();

        map.put("code",-3);
        map.put("msg","您没有访问权限");

        return map;
    }
}
