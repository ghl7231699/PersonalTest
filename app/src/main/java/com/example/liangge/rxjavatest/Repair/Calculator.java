package com.example.liangge.rxjavatest.Repair;

import com.example.liangge.rxjavatest.di.Replace;

/**
 * Created by guhongliang on 2017/8/12.
 * 修复bug的类（服务端修复，模拟）
 */

public class Calculator {
    @Replace(clazz = "com.example.liangge.rxjavatest.bean.Calculator", method = "calculator")
    public int calculator() {
        int i = 1;
        int j = 10;
        return j / i;
    }
}
