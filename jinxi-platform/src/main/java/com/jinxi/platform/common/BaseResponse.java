package com.jinxi.platform.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 全局统一接口响应对象。
 *
 * @param <T> 响应数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    private Integer code = ResultCode.SUCCESS.getCode();

    private String message;

    private T data;

    private List<T> list;

    private long totalCount;

}
