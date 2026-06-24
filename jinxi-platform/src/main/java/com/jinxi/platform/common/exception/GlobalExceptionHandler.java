package com.jinxi.platform.common.exception;

import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.common.util.ResultUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ResultUtil.resErrorResult(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Void> handleValidException(MethodArgumentNotValidException e) {
        String msg = "参数校验失败";
        if (e.getBindingResult().getFieldError() != null) {
            msg = e.getBindingResult().getFieldError().getDefaultMessage();
        }
        return ResultUtil.resErrorResult(msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        return ResultUtil.resErrorResult(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultUtil.resErrorResult("系统繁忙");
    }
}
