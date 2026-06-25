package com.jinxi.platform.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.common.PageResult;
import com.jinxi.platform.common.ResultCode;

import java.util.List;

public class ResultUtil {

    public static <T> BaseResponse<T> success(T data) {
        return success(data, ResultCode.SUCCESS.getMessage());
    }

    public static <T> BaseResponse<T> successMessage(String message) {
        return success(null, message);
    }

    public static <T> BaseResponse<T> success() {
        return success(null, ResultCode.SUCCESS.getMessage());
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<PageResult<T>> page(IPage<?> page, Class<T> targetClass) {
        List<T> records = ConvertUtil.convertWithRowNum(page.getRecords(), targetClass);
        PageResult<T> pageResult = PageResult.of(records, page.getTotal(), page.getCurrent(), page.getSize());
        return success(pageResult);
    }

    public static <T> BaseResponse<T> error(String message) {
        return error(null, ResultCode.ERROR.getCode(), message);
    }

    public static <T> BaseResponse<T> error(Integer code, String message) {
        return error(null, code, message);
    }

    public static <T> BaseResponse<T> error(T data, Integer code, String message) {
        return BaseResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

}
