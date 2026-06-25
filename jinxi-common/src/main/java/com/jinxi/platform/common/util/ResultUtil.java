package com.jinxi.platform.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.common.PageResult;
import com.jinxi.platform.common.ResultCode;

import java.util.List;

public class ResultUtil {
    /**
     * 返回成功响应，携带业务数据。
     *
     * @param data 业务数据
     * @param <T>  业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccesResult(T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.SUCCESS.getCode());
        baseResponse.setMessage(ResultCode.SUCCESS.getMessage());
        baseResponse.setData(data);
        return baseResponse;
    }
    /**
     * 返回成功响应，不携带业务数据。
     *
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccesResult(String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.SUCCESS.getCode());
        baseResponse.setMessage(message);
        return baseResponse;
    }

    /**
     * 返回成功响应，不携带业务数据。
     *
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccesResult() {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.SUCCESS.getCode());
        baseResponse.setMessage(ResultCode.SUCCESS.getMessage());
        return baseResponse;
    }
    /**
     * 返回成功响应，携带业务数据。
     *
     * @param data 业务数据
     * @param <T>  业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccesResult(T data,String messagae) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.SUCCESS.getCode());
        baseResponse.setMessage(messagae);
        baseResponse.setData(data);
        return baseResponse;
    }
    /**
     * 返回成功响应，携带业务数据和总数。
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    @Deprecated
    public static <T> BaseResponse<T> resSuccesResult(T data, long totalCount) {
        return resSuccesResult(data);
    }
    /**
     * 返回成功响应，携带分页数据。
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<PageResult<T>> resSuccessPageResult(IPage<?> page, Class<T> targetClass) {
        List<T> records = ConvertUtil.convertWithRowNum(page.getRecords(), targetClass);
        PageResult<T> pageResult = PageResult.of(records, page.getTotal(), page.getCurrent(), page.getSize());
        return resSuccesResult(pageResult);
    }
    /**
     * 返回响应，携带业务数据不携带总数。
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccessResult(T data, Integer code, String message) {
        return resSuccessResult(data,code, message,0);
    }
    /**
     * 返回响应，携带业务数据和总数。
     * @deprecated 封装成功返回接口
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    @Deprecated
    public static <T> BaseResponse<T> resSuccessResult(T data,Integer code, String message, long totalCount) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        baseResponse.setData(data);
        return baseResponse;
    }

    public static <T> BaseResponse<T> resSuccessResult(List<T> data, Class<T> clazz,Integer code, String message){
        return (BaseResponse<T>) resSuccessResult(ConvertUtil.convert(data,clazz),code,message,data.size());
    }
    /**
     * 返回失败响应。
     *
     * @param message 错误信息
     * @param <T>     业务数据类型
     * @return 统一失败响应
     */
    public static <T> BaseResponse<T> resErrorResult(String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.ERROR.getCode());
        baseResponse.setMessage(message);
        return baseResponse;
    }

    /**
     * 返回指定错误码的失败响应。
     *
     * @param code    错误码
     * @param message 错误信息
     * @param <T>     业务数据类型
     * @return 统一失败响应
     */
    public static <T> BaseResponse<T> resErrorResult(Integer code, String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        return baseResponse;
    }
    /**
     * 返回失败响应。
     *
     * @param message 错误信息
     * @param <T>     业务数据类型
     * @return 统一失败响应
     */
    public static <T> BaseResponse<T> resErrorResult(T data,Integer code, String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        baseResponse.setData(data);
        return baseResponse;
    }


}
