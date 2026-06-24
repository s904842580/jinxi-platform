package com.jinxi.platform.vo;

import com.jinxi.platform.vo.User.UserListVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录接口响应数据。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    private String token;

    private UserListVO user;
}
