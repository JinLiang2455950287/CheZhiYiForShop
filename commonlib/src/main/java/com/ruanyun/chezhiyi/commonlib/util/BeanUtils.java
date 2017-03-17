package com.ruanyun.chezhiyi.commonlib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {

    public static String toString(Object obj) {
        StringBuffer sb = new StringBuffer();
        Class<?> clz = obj.getClass();
        Method[] methods = clz.getMethods();
        String methodName;
        String attrValue;
        try {
            for (Method method : methods) {

                methodName = method.getName();
                if (!methodName.startsWith("get") || "getClass".equals(methodName)) {
                    continue;
                }
                Object value = method.invoke(obj);
                attrValue = "";
                if (value == null) {
                    continue;
                }
                if (value instanceof Object[]) {
                    Object[] objects = (Object[]) value;
                    if (objects == null || objects.length == 0)
                        continue;
                    for (Object obj1 : objects) {
                        attrValue = attrValue + "," + obj1.toString();
                    }
                    if (attrValue != null && !attrValue.isEmpty()) {
                        attrValue = attrValue.substring(1);
                    }
                } else {
                    attrValue = value.toString();
                }
                sb.append("&").append(methodName.substring(3, 4).toLowerCase() + methodName.substring(4)).append("=").append(attrValue);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (sb.length() != 0)
            return sb.substring(1);
        return sb.toString();
    }

}
