package com.jinxi.platform.service.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinxi.platform.entity.User;
import com.jinxi.platform.enums.AccountTypeEnum;
import com.jinxi.platform.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * User 数据访问层（Repository）
 * 封装所有对 User 表的查询操作
 */
@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
    // ============ 单一查询 ============

    public User findByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
    public User findByEmail(String email) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }
    public User findByPhone(String phone) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
    }

    public User findByAccount(String account, AccountTypeEnum accountType) {
        return switch (accountType) {
            case EMAIL -> findByEmail(account);
            case PHONE -> findByPhone(account);
            case USERNAME -> findByUsername(account);
        };
    }

    // ============ 存在性检查 ============

    public boolean existsByUsername(String username) {
        return this.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) > 0;
    }
    public boolean existsByEmail(String email) {
        return this.count(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) > 0;
    }
    public boolean existsByPhone(String phone) {
        return this.count(new LambdaQueryWrapper<User>().eq(User::getPhone, phone)) > 0;
    }
    // ============ 其他通用查询 ============

    public User findById(Long id) {
        return this.getById(id);
    }
}
