package com.jinxi.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinxi.platform.entity.User;
import com.jinxi.platform.mapper.UserMapper;


@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String test() {
        return "hello platform";
    }
    @GetMapping("/user")
    public List<User> list() {
    return userMapper.selectList(null);
}

}