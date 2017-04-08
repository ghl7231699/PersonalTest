package com.example.liangge.rxjavatest.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by liangge on 2017/4/8.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ViewScope {
}
