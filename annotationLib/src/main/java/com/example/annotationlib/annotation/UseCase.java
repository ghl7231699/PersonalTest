package com.example.annotationlib.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类描述：标准注解使用
 * 创建人：ghl
 * 创建时间：2018/10/15
 *
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD})
public @interface UseCase {
    int id();

    /**
     * 可以通过 default 为指定的元素指定一个默认值，如果用户没有为其指定值，就使用默认值。
     *
     * @return
     */
    String description() default "default value";
}
