package com.jinxi.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinxi.platform.common.util.JwtUtil;
import com.jinxi.platform.dto.LoginDTO;
import com.jinxi.platform.dto.UserUpdateDTO;
import com.jinxi.platform.service.repository.UserRepository;
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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository  userRepository;
    private final JwtUtil jwtUtil;
    @Override
    public void add(UserAddDTO dto) {
        // 1.检查唯一性
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("用户名已存在");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("邮箱已被注册");
        }
        if (userRepository.existsByPhone(dto.getPhone())) {
            throw new BusinessException("手机号已被注册");
        }
        // 2. 添加用户
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        // 3. 加密密码
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        boolean row = save(user);
        if (!row){
            throw new BusinessException("注册失败");
        }
    }

    @Override
    public void update(UserUpdateDTO dto) {
        User existing = userRepository.findById(dto.getId());
        if (existing == null) {
            throw new BusinessException("用户不存在");
        }
        // 检查手机号是否被他人占用
        if (StringUtils.hasText(dto.getPhone()) && !dto.getPhone().equals(existing.getPhone())) {
            if (userRepository.existsByPhone(dto.getPhone())) {
                throw new BusinessException("该手机已经注册");
            }
        }
        // 检查邮箱是否被他人占用
        if (StringUtils.hasText(dto.getEmail()) && !dto.getEmail().equals(existing.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new BusinessException("该邮箱已经注册");
            }
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        if (!userRepository.updateById(user)) {
            throw new BusinessException("更新失败");
        }
    }
    // 登录
    @Override
    public String login(LoginDTO dto) {
        User user = null;
        // 根据不同的登录方式查询
        if (StringUtils.hasText(dto.getUsername())) {
            user = userRepository.findByUsername(dto.getUsername());
        } else if (StringUtils.hasText(dto.getEmail())) {
            user = userRepository.findByEmail(dto.getEmail());
        } else if (StringUtils.hasText(dto.getPhone())) {
            user = userRepository.findByPhone(dto.getPhone());
        } else {
            throw new BusinessException("请提供用户名、邮箱或手机号");
        }

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        // 校验密码
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new BusinessException("用户名或密码错误");
        }
        // 生成token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return token;
    }

}
