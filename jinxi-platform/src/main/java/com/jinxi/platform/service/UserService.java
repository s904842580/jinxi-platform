package com.jinxi.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinxi.platform.dto.LoginDTO;
import com.jinxi.platform.dto.UserAddDTO;
import com.jinxi.platform.dto.UserUpdateDTO;
import com.jinxi.platform.entity.User;
import jakarta.validation.Valid;

public interface UserService extends IService<User> {
    void add(UserAddDTO dto);
    void update(UserUpdateDTO dto);
    String login(LoginDTO dto);
}
