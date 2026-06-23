package com.jinxi.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinxi.platform.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinxi.platform.common.exception.BusinessException;
import com.jinxi.platform.dto.UserAddDTO;
import com.jinxi.platform.entity.User;
import com.jinxi.platform.mapper.UserMapper;
import com.jinxi.platform.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final PasswordEncoder passwordEncoder;
    @Override
    public void add(UserAddDTO dto) {
        // 1. 检查用户名是否已存在
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUsername, dto.getUsername());
        if (this.count(userWrapper) > 0){
            throw new BusinessException("用户名已存在");
        }
        // 2. 添加用户
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        // 3. 加密密码
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        boolean row = save(user);
        if (!row){
            throw new BusinessException("添加用户失败");
        }
    }

    @Override
    public void update(UserUpdateDTO dto) {
        User user = new User();
        if (dto != null){
            BeanUtils.copyProperties(dto,user);
        }else {
            throw new BusinessException("更新用户失败");
        }
        boolean row = updateById(user);
        if (!row){
            throw new BusinessException("更新用户失败");
        }
    }

}
