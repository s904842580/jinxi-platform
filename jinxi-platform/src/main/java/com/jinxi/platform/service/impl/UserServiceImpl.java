package com.jinxi.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.common.context.UserContext;
import com.jinxi.platform.common.exception.ServiceException;
import com.jinxi.platform.common.util.JwtUtil;
import com.jinxi.platform.common.util.ResultUtil;
import com.jinxi.platform.dto.LoginDTO;
import com.jinxi.platform.dto.user.UserAddDTO;
import com.jinxi.platform.dto.user.UserListDTO;
import com.jinxi.platform.dto.user.UserUpdateDTO;
import com.jinxi.platform.entity.User;
import com.jinxi.platform.enums.AccountTypeEnum;
import com.jinxi.platform.mapper.UserMapper;
import com.jinxi.platform.service.UserService;
import com.jinxi.platform.service.repository.UserRepository;
import com.jinxi.platform.vo.LoginVO;
import com.jinxi.platform.vo.User.UserListVO;
import com.jinxi.platform.vo.User.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public BaseResponse<?> list(UserListDTO dto) {
        Page<User> userPage =new Page<>(dto.getPage(), dto.getPageSize());
        IPage<User> page = this.page(userPage);
//        List<UserVO> users = page.getRecords().stream()
//                .map(UserVO::from)
//                .toList();
        return ResultUtil.page(page, UserListVO.class);
    }

    @Override
    public BaseResponse<?> getUserById(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtil.success(userVO, "查询成功");
    }

    @Override
    public BaseResponse<?> add(UserAddDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ServiceException("用户名已存在");
        }
        if (StringUtils.hasText(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new ServiceException("邮箱已被注册");
        }
        if (StringUtils.hasText(dto.getPhone()) && userRepository.existsByPhone(dto.getPhone())) {
            throw new ServiceException("手机号已被注册");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!save(user)) {
            throw new ServiceException("注册失败");
        }
        return ResultUtil.successMessage("注册成功");
    }

    @Override
    public BaseResponse<?> update(UserUpdateDTO dto) {
        User existing = userRepository.findById(dto.getId());
        if (existing == null) {
            throw new ServiceException("用户不存在");
        }
        if (StringUtils.hasText(dto.getPhone()) && !dto.getPhone().equals(existing.getPhone())
                && userRepository.existsByPhone(dto.getPhone())) {
            throw new ServiceException("该手机号已经注册");
        }
        if (StringUtils.hasText(dto.getEmail()) && !dto.getEmail().equals(existing.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new ServiceException("该邮箱已经注册");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (!userRepository.updateById(user)) {
            throw new ServiceException("更新失败");
        }
        return ResultUtil.successMessage("更新成功");
    }

    @Override
    public BaseResponse<?> deleteById(Long id) {
        if (getById(id) == null) {
            throw new ServiceException("用户不存在");
        }
        if (!removeById(id)) {
            throw new ServiceException("删除失败");
        }
        return ResultUtil.successMessage("删除成功");
    }

    @Override
    public BaseResponse<?> login(LoginDTO dto) {
        String account = dto.getAccount().trim();
        AccountTypeEnum accountType = AccountTypeEnum.resolve(account);
        User user = userRepository.findByAccount(account, accountType);

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ServiceException("账号或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return ResultUtil.success(new LoginVO(token, UserListVO.from(user)), "登录成功");
    }
}
