package com.example.liangge.rxjavatest.bean;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.liangge.rxjavatest.common.utils.LogUtil;

/**
 * Created by guhongliang on 2018/2/23.
 */

public class Man {
    public void show1(String s) {
        LogUtil.d("Reflect Method", "调用了：公有的，String参数的show1(): s = " + s);
    }

    protected void show2() {
        LogUtil.d("Reflect Method", "调用了：受保护的，无参的show2()");
    }

    void show3() {
        LogUtil.d("Reflect Method", "调用了：默认的，无参的show3()");
    }

    @NonNull
    private String show4(int age) {
        LogUtil.d("Reflect Method", "调用了，私有的，并且有返回值的，int参数的show4(): age = " + age);
        return "abcd";
    }

    private void show5(TextView view, Fruit fruit, int num) {
        if (view != null) {
            view.append("我还有" + fruit.getName() + num + "个");
        }
    }

}
