package com.jinxi.platform.common.exception;

import com.jinxi.platform.common.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

@Slf4j
@Getter
@Setter
public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private ResultCode resultCode;

    private Integer code;

    public ServiceException(Integer code, String message){
        super(message);
        this.code = code;
    }
    public ServiceException(String message){
        super(message);
        this.resultCode = ResultCode.FAILURE;
        this.code = ResultCode.FAILURE.getCode();
    }
    public  ServiceException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }
    public ServiceException(ResultCode resultCode, String message){
        super(message);
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }
    public ServiceException(ResultCode resultCode, String message, Throwable cause){
        super(message, cause);
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace(){
        return this.fillInStackTrace();
    }
}
