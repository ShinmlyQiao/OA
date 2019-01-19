package com.xingxing.oa.reflect;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 反射指定类的属性，包括父类、父接口属性
 */
public class ScanFieldUtil {

    public static List<Field> getClassField(Class clazz){
        Class clazzBak = clazz;
        List<Field> fieldList = new ArrayList<>(24);
        //反射父类
        while(!Object.class.equals(clazz)&& Objects.nonNull(clazz)){
            Field[] declaredFields = clazz.getDeclaredFields();
            if(Objects.nonNull(declaredFields)&& declaredFields.length>0) {
                fieldList.addAll(List.of(declaredFields));
            }
            clazz = clazz.getSuperclass();
        }
        //反射父接口
        if(Objects.nonNull(clazzBak)){
            Class[] interfaces = clazzBak.getInterfaces();
            if(Objects.nonNull(interfaces)&&interfaces.length>0){
                Deque<Class> deque = new ArrayDeque(16);
                deque.addAll(List.of(interfaces));
                while(!deque.isEmpty()){
                    Class inter = deque.pollFirst();
                    Field[] declaredFields = inter.getDeclaredFields();
                    if(Objects.nonNull(declaredFields)&&declaredFields.length>0){
                        fieldList.addAll(List.of(declaredFields));
                    }
                    Class[] superInterfaces = inter.getInterfaces();
                    if(Objects.nonNull(superInterfaces)&&superInterfaces.length>0){
                        deque.addAll(List.of(superInterfaces));
                    }

                }

            }
        }
        return fieldList;
    }

    /**
     * 获取公共的，及自己私有的
     * @param clazz
     * @return
     */
    public static List<Field> getClassFieldSelf(Class clazz){
        Objects.requireNonNull(clazz);
        Set<Field> fieldSet = new HashSet<>(32);
        fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fieldSet.addAll(Arrays.asList(clazz.getFields()));
        return List.copyOf(fieldSet);
    }
}
