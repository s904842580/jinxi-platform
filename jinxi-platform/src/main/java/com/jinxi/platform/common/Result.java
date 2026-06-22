package com.jinxi.platform.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> success(T data){
        return new Result<>(
                ResultCode.SUCCESS,
                "success",
                data
        );
    }

    public static <T> Result<T> success(){
        return new Result<>(
                ResultCode.SUCCESS,
                "success",
                null
        );
    }

    public static <T> Result<T> fail(String msg){
        return new Result<>(
                ResultCode.ERROR,
                msg,
                null
        );
    }
}