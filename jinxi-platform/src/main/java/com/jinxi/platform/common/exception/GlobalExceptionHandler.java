package com.jinxi.platform.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jinxi.platform.common.Result;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(
        BusinessException e){
            log.error("业务异常",e);
            return Result.fail(e.getMessage());
        }
    @ExceptionHandler(Exception.class)
    public Result<Void> handExcResult(
        Exception e){
            log.error("系统异常",e);
            return Result.fail("系统繁忙");
        }
    // 处理Validation异常
    @ExceptionHandler(
        MethodArgumentNotValidException.class
    )
    public Result<Void> handleValidException(
        MethodArgumentNotValidException e
    ){
        String msg = e.getBindingResult()
                    .getFieldError()
                    .getDefaultMessage();
        return Result.fail(msg);
    }
}
