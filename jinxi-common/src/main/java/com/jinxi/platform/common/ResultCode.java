package com.jinxi.platform.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
   SUCCESS(0, "success"),
    OK(200,"OK"),
    ERROR(500,"error"),
    FAILURE(401,"业务处理错误"),
    UNAUTHORIZED(402,"请求未经授权"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"未找到方法"),;
   final Integer code;
   final String message;

}
