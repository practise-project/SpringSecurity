package com.example.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户控制器
 * @author ClowLAY
 * create date 2020/5/23
 */

@Controller
public class UserController {

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "web/index";
    }


    /**
     * 登录页面
     * @return
     */

    @GetMapping("/login")
    public String login(){
        return "web/login/login";
    }





}
