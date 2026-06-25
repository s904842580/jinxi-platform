package com.jinxi.platform.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    public static <T> T convert(T data, Class elementType){
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
