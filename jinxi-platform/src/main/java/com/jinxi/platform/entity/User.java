package com.jinxi.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")
public class User {

    private Long id;

    private String username;

    private String password;

}