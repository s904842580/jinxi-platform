package com.jinxi.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String nickname;
    private String avatar;
    private String email;
    private String phone;
}
