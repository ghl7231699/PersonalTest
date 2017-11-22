package com.example.liangge.rxjavatest.ndk;

/**
 * Created by guhongliang on 2017/11/22.
 * 自定义的classLoader
 */

public class CustomerClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
