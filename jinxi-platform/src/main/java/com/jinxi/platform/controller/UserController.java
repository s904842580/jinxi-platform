package com.jinxi.platform.controller;

import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.dto.LoginDTO;
import com.jinxi.platform.dto.user.UserAddDTO;
import com.jinxi.platform.dto.user.UserListDTO;
import com.jinxi.platform.dto.user.UserUpdateDTO;
import com.jinxi.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/list")
    public BaseResponse<?> list(@RequestBody UserListDTO dto) {
        return userService.list(dto);
    }

    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody @Valid UserAddDTO dto) {
        return userService.add(dto);
    }

    @GetMapping("/{id}")
    public BaseResponse<?> get(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<?> delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @PutMapping("/update")
    public BaseResponse<?> update(@RequestBody @Valid UserUpdateDTO dto) {
        return userService.update(dto);
    }

    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody @Valid LoginDTO dto) {
        return userService.login(dto);
    }
}
