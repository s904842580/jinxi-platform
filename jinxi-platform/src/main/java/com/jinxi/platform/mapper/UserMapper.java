package com.jinxi.platform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinxi.platform.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
