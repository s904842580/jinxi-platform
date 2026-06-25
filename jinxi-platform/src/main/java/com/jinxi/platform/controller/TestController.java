package com.jinxi.platform.controller;

import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.common.exception.BusinessException;
import com.jinxi.platform.common.util.ResultUtil;
import com.jinxi.platform.mapper.UserMapper;
import com.jinxi.platform.service.UserService;
import com.jinxi.platform.vo.User.UserListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping
    public BaseResponse<String> test() {
        return ResultUtil.success("hello platform");
    }

    @GetMapping("/user")
    public BaseResponse<List<UserListVO>> list() {
        List<UserListVO> users = userMapper.selectList(null).stream()
                .map(UserListVO::from)
                .toList();
        return ResultUtil.success(users);
    }

    @GetMapping("/error")
    public BaseResponse<String> error() {
        throw new BusinessException("异常测试");
    }
}
