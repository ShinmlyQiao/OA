package com.xingxing.oa.annotions;


import java.lang.annotation.*;

/**
 * 实体类注解的id生成器
 * 建议指定值为  服务名::类名::属性名
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Inherited
public @interface GeneratorId {
    String value();
}
