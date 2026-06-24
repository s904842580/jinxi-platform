package com.jinxi.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.dto.LoginDTO;
import com.jinxi.platform.dto.user.UserAddDTO;
import com.jinxi.platform.dto.user.UserListDTO;
import com.jinxi.platform.dto.user.UserUpdateDTO;
import com.jinxi.platform.entity.User;

public interface UserService extends IService<User> {

    BaseResponse<?> list(UserListDTO dto);

    BaseResponse<?> getUserById(Long id);

    BaseResponse<?> add(UserAddDTO dto);

    BaseResponse<?> update(UserUpdateDTO dto);

    BaseResponse<?> deleteById(Long id);

    BaseResponse<?> login(LoginDTO dto);
}
