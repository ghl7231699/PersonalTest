package com.example.liangge.rxjavatest.common.utils;

/**
 * Created by liangge on 2017/4/8.
 */

public class Preconditions {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
