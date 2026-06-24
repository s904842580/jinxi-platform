package com.jinxi.platform.common.util;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jinxi.platform.common.BaseResponse;
import com.jinxi.platform.common.ResultCode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
     * @deprecated 封装成功返回接口
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
     * @deprecated 封装成功返回接口
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccesResult(T data, long totalCount) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.SUCCESS.getCode());
        baseResponse.setMessage(ResultCode.SUCCESS.getMessage());
        baseResponse.setData(data);
        baseResponse.setTotalCount(totalCount);
        return baseResponse;
    }
    /**
     * 返回成功响应，携带分页数据。
     * @deprecated 封装成功返回接口
     * @param <T> 业务数据类型
     * @return 统一成功响应
     */
    public static <T> BaseResponse<T> resSuccessPageResult(IPage page, Class<T> targetClass) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ResultCode.SUCCESS.getCode());
        baseResponse.setMessage(ResultCode.SUCCESS.getMessage());
        baseResponse.setList(ConvertUtil.convertWithRowNum(page.getRecords(),targetClass));
        baseResponse.setTotalCount(page.getTotal());
        return baseResponse;
    }
    /**
     * 返回响应，携带业务数据不携带总数。
     * @deprecated 封装成功返回接口
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
    public static <T> BaseResponse<T> resSuccessResult(T data,Integer code, String message, long totalCount) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        baseResponse.setData(data);
        baseResponse.setTotalCount(totalCount);
        return baseResponse;
    }

    public static <T> BaseResponse<T> resSuccessResult(List<T> data, Class<T> clazz,Integer code, String message){
        return (BaseResponse<T>) resSuccessResult(convert(data,clazz),code,message,data.size());
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

    private static <T> T convert(T data, Class elementType){
        // 如果data是List类型
        if(data instanceof List<?>){
            List<?> list = (List<?>)data;
            if(list == null || list.isEmpty() || allElementNull(list)){
                if(elementType == null){
                    // 如果无法获取泛型类型，返回空列表(或可选，返回单个null，但不推荐)
                    return null;
                }
                try{
                    // 创建该类型的新实例
                    Object emptyObj = elementType.getDeclaredConstructor().newInstance();

                    // 使用反射将所有String类型字符串设为空字符串
                    setAllStringFieldsToEmptyString(emptyObj);

                    // 创建新的 List，只包含这个空对象
                    List<Object> result = new ArrayList<>();
                    result.add(emptyObj);

                    // 强制类型转换(安全，因为是同一类型)
                    T typedResult = (T) result;
                    data = typedResult;
                }catch (Exception e){
                    log.error("无法创建类型实例: " + elementType.getName(), e);
                    // 创建失败，返回空列表
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return data;
    }
    /**
     * 判断集合是否全部为空
     */

    private static boolean allElementNull(List<?> list) {
        for (Object obj : list) {
            if (obj != null) {
                return false;
            }
        }
        return true;
}
    /**
     * 使用反射将对象的所有String类型字段设置为空字符串
     */
    private static void setAllStringFieldsToEmptyString(Object obj){
        if(obj == null) return;

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            try{
                Class<?> type = field.getType();
                Object defaultValue = null;

                if (type == String.class){
                    defaultValue = ""; // 对于String类型，默认值是空字符串()
                }else if(type == Integer.class || type == int.class){
                    defaultValue = 0; // 对于整型，默认值是0
                } else if (type == Long.class || type == long.class) {
                    defaultValue = 0L; // 对于长整型，默认值是0L
                } else if (type == Double.class || type == double.class) {
                    defaultValue = 0.0; // 对于双精度浮点型，默认值是0.0
                } else if (type == Boolean.class || type == boolean.class) {
                    defaultValue = false; // 对于布尔型，默认值是false
                } else if (type == Float.class || type == float.class) {
                    defaultValue = 0.0f; // 对于单精度浮点型，默认值是0.0f
                } else if (type == Short.class || type == short.class) {
                    defaultValue = (short) 0; // 对于短整型，默认值是(short) 0
                } else if (type == Byte.class || type == byte.class) {
                    defaultValue = (byte) 0; // 对于字节型，默认值是(byte) 0
                } else if (type == BigDecimal.class){
                    defaultValue = BigDecimal.ZERO;
                }
                if (defaultValue != null){
                    field.set(obj, defaultValue);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

}