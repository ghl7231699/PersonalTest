package com.example.liangge.rxjavatest.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by guhongliang on 2017/8/12.
 */

@Target(ElementType.METHOD) //注解的类型
@Retention(RetentionPolicy.RUNTIME) //注解的时机 运行时
public @interface Replace {
    String clazz();

    String method();
}
