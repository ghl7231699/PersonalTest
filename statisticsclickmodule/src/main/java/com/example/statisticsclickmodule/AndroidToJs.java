package com.example.statisticsclickmodule;

import android.webkit.JavascriptInterface;

/**
 * Created by guhongliang on 2017/9/14.
 */

public class AndroidToJs extends Object {
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void sayHello(String msg) {
//        System.out.print("Js调用了Android的SayHello方法");
        System.out.print(msg);

    }
}
