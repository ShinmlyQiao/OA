package com.xingxing.oa.annotions;


import java.lang.annotation.*;

/**
 * 实体类注解的id生成器
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Inherited
public @interface GeneratorId {
    String value();
}
