package com.jinxi.platform.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 通用转换工具类，支持反射自动映射字段 + 自动添加rowNum
 */
@Slf4j
public class ConvertUtil {
    /**
     * 将源列表转换为目标列表，自动通过字段名匹配进行赋值，并添加顺序号rowNum
     *
     * @param sourceList 源列表
     * @param targetClass  目标类
     * @return 转换后的列表
     */
    public static <S,T> List<T> convertWithRowNum(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null) return Collections.emptyList();
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
                    // 设置顺序号
                    setRowNUm(target,i + 1, targetClass);
                    return target;
                }).collect(Collectors.toList());
    }
    /**
     * 通过反射设置rowNum字段
     */
    private static <T> void setRowNUm(T target, Integer rowNum, Class<T> targetClass) {
       try{
           Method method = targetClass.getMethod("setRowNum", Integer.class);
           method.invoke(target, rowNum);
       }catch (Exception e){
           throw new RuntimeException("目标类" + targetClass.getName() + "未找到 setRowNum(Integer)方法", e);
       }
    }
}
