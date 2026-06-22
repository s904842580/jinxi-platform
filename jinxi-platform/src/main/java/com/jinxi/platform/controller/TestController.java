package com.jinxi.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinxi.platform.common.Result;
import com.jinxi.platform.entity.User;
import com.jinxi.platform.mapper.UserMapper;


@RestController
@RequestMapping("/test")
public class TestController {
    private final UserMapper userMapper;

    TestController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping
    public Result<String> test() {
        return Result.success(
            "hello platform"
        );
    }
    @GetMapping("/user")
    public List<User> list() {
    return userMapper.selectList(null);
}

}