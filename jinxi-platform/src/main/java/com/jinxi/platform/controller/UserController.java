package com.jinxi.platform.controller;

import com.jinxi.platform.dto.LoginDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinxi.platform.common.Result;
import com.jinxi.platform.common.exception.BusinessException;
import com.jinxi.platform.dto.UserAddDTO;
import com.jinxi.platform.dto.UserUpdateDTO;
import com.jinxi.platform.entity.User;
import com.jinxi.platform.service.UserService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

import java.sql.Wrapper;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @GetMapping("/list")
    public Result<List<User>> list() {
        return Result.success(
            userService.list()
        );
    }
    // 新增用户
    @PostMapping("/add")
    public Result<Void> add(@RequestBody @Valid UserAddDTO dto) {
        userService.add(dto);
        return Result.success();
    }
    // 根据ID查询
    @GetMapping("/{id}")
    public Result<User> get(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }
    // 删除用户
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id){
        userService.removeById(id);
        return Result.success();
    }
    // 修改用户
    @PutMapping("/update")
    public Result<Void> update(@RequestBody@Valid UserUpdateDTO dto) {
        userService.update(dto);
        return Result.success();
    }
    // 登录
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid LoginDTO dto) {
        return Result.success(userService.login(dto));
    }

}
