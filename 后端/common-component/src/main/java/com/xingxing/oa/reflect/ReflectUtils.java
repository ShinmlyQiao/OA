package com.xingxing.oa.reflect;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 反射类的某一个方法或者属性
 */
public class ReflectUtils {

    /**
     * 循环继承的父类,根据方法名反射
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getDeclaredMethod(Class clazz, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        Class clazzBak = clazz;
        while (!Object.class.equals(clazz) && Objects.isNull(method)) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
            }
            clazz = clazz.getSuperclass();
        }
        if (Objects.isNull(method)) {
            method = getIDeclaredMethod(clazzBak, methodName, parameterTypes);
        }
        return method;
    }

    /**
     * 反射接口中方法
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    private static Method getIDeclaredMethod(Class clazz, String methodName, Class<?>... parameterTypes) {
        Class[] interfaces = clazz.getInterfaces();
        if (Objects.nonNull(interfaces) && interfaces.length > 0) {
            for (Class inter : interfaces) {
                try {
                    Method method = inter.getDeclaredMethod(methodName, parameterTypes);
                    if(Objects.nonNull(method)){
                        return method;
                    }
                    method = getIDeclaredMethod(inter,methodName,parameterTypes);
                    if(Objects.nonNull(method)){
                        return method;
                    }
                } catch (NoSuchMethodException e) {
                }
            }
        }
        return null;
    }

    /**
     * 循环继承的属性,根据方法名反射
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getDeclaredField(Class clazz, String fieldName) {
        Field field = null;
        Class clazzBak = clazz;
        while (!Object.class.equals(clazz) && field == null) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
            clazz = clazz.getSuperclass();
        }
        if(Objects.isNull(field)){
            field = getIDeclaredField(clazzBak,fieldName);
        }
        return field;
    }

    private static Field getIDeclaredField(Class clazz, String fieldName) {
        Class[] interfaces = clazz.getInterfaces();
        if (Objects.nonNull(interfaces) && interfaces.length > 0) {
            for (Class inter : interfaces) {
                try {
                    Field field = inter.getDeclaredField(fieldName);
                    if(Objects.nonNull(field)){
                        return field;
                    }
                    field = getIDeclaredField(inter,fieldName);
                    if(Objects.nonNull(field)){
                        return field;
                    }
                } catch (NoSuchFieldException e) {
                }
            }
        }
        return null;
    }


    public static Object invokeMethod(Object instance, Method method, Object... parameters) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFieldValue(Object instance, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object instance, Field field) {
        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
