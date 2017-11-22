package com.example.liangge.rxjavatest.ndk.CustomFix;

import java.lang.reflect.Method;

/**
 * 类名称：
 * 类描述：jni方法的实现,模拟的是andFix的native方法的加载
 * 创建人：ghl
 * 创建时间：2017/11/18 下午9:36
 * 修改人：ghl
 * 修改时间：2017/11/18 下午9:36
 *
 * @version v1.0
 */

public class HandlerNative {

    static {
        System.loadLibrary("native-lib");
    }

    public static native void init(int apiCode);//对andFix版本进行校验

    public static native void replaceMethod(Method method, Method dest);//通过JNI的方法，在native层面进行方法的替换
}
