package com.yanzhang.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    /**
     * 普通web项目，跳转到login页面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(String name, String password) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(name,password);

        Map<String, Object> map = new HashMap<>();

//        try {
//            subject.login(token); //登录
//        }catch (UnknownAccountException exception) {
//            System.out.println("用户名不存在");
//        }catch (IncorrectCredentialsException exception) {
//            System.out.println("密码错误");
//        }catch (AuthenticationException exception) {
//            System.out.println("其他错误");
//        }

        try{
            subject.login(token);
            map.put("code", 1);
            map.put("msg", "登录成功");
            map.put("sessionId", subject.getSession().getId());
        }catch (AuthenticationException ex) {
            map.put("code", -1);
            map.put("msg", "用户名或密码不存在");
        }

        return map;
    }

    /**
     * 前后端分离项目，返回提示信息
     * @return
     */
    @RequestMapping("/no_login")
    @ResponseBody
    public Object noLogin() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",-2);
        map.put("msg","您暂未登录");

        return map;
    }
}
