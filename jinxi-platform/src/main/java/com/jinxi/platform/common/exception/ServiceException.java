package com.jinxi.platform.common.exception;

import com.jinxi.platform.common.ResultCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

@Slf4j
@Data
public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private ResultCode resultCode;

    private String message;

    private Integer code;

    public ServiceException(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    public ServiceException(String message){
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }
    public  ServiceException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
    public ServiceException(ResultCode resultCode, String message){
        super(message);
        this.resultCode = resultCode;
    }
    public ServiceException(ResultCode resultCode, String message, Throwable cause){
        super(message, cause);
        this.resultCode = resultCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace(){
        return this.fillInStackTrace();
    }
}
