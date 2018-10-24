package com.example.liangge.rxjavatest.annotation;


import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindLayout {
    @LayoutRes
    int layout();
}
