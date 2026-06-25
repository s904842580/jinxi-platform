package com.jinxi.platform.common.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 通用转换工具类，支持反射自动映射字段 + 自动添加rowNum
 */
public class ConvertUtil {
    /**
     * 将源列表转换为目标列表，自动通过字段名匹配进行赋值，并添加顺序号rowNum
     *
     * @param sourceList 源列表
     * @param targetClass  目标类
     * @return 转换后的列表
     */
    public static <S,T> List<T> convertWithRowNum(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }

        Method rowNumSetter = findRowNumSetter(targetClass);
        return IntStream.range(0, sourceList.size())
                .mapToObj(i -> {
                    S source = sourceList.get(i);
                    T target;
                    try{
                        target = targetClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("无法实例化目标类:" + targetClass.getName(),e);
                    }
                    BeanUtils.copyProperties(source,target);
                    setRowNum(target, i + 1, rowNumSetter);
                    return target;
                }).collect(Collectors.toList());
    }

    private static Method findRowNumSetter(Class<?> targetClass) {
        try {
            return targetClass.getMethod("setRowNum", Integer.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static void setRowNum(Object target, Integer rowNum, Method rowNumSetter) {
        if (rowNumSetter == null) {
            return;
        }
        try {
            rowNumSetter.invoke(target, rowNum);
        } catch (Exception e) {
            throw new RuntimeException("设置rowNum失败", e);
        }
    }
}
