package com.example.liangge.rxjavatest.common.utils;

import android.content.Context;
import android.util.Log;

import com.example.liangge.rxjavatest.di.Replace;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

import static android.content.ContentValues.TAG;

/**
 * Created by guhongliang on 2017/8/12.
 */

public class DexManager {
    private Context mContext;

    public DexManager(Context context) {
        mContext = context;
    }

    public void loadDex(File file) {
        File opFile = new File(mContext.getCacheDir(), file.getName());
        if (opFile.exists()) {
            opFile.delete();
        }
        try {
            //加载dex
            DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(), "", Context.MODE_PRIVATE);
            //遍历dex里面的class
            Enumeration<String> enumeration = dexFile.entries();
            while (enumeration.hasMoreElements()) {
                String className = enumeration.nextElement();
                //修复好的class 怎样找到bug class
                Class realClazz = dexFile.loadClass(className, mContext.getClassLoader());
                Log.e(TAG, "找到类 " + className);
                //修复
                fix(realClazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fix(Class realClazz) {
        //拿到注解
        Method[] method = realClazz.getDeclaredMethods();
        for (Method m : method
                ) {
            Replace replace = m.getAnnotation(Replace.class);
            if (replace == null) {
                continue;
            }
            String wrongClazzName = replace.clazz();
            String wrongMethodName = replace.method();

            try {
                Class wrongClass = Class.forName(wrongClazzName);
                try {
                    //最终拿到错误的method对象
                    Method wrongMethod = wrongClass.getMethod(wrongMethodName, m.getParameterTypes());
                    //修复
//                    replace(wrongMethod, method);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //调用Native方法（替换）
//    private native void replace(Method wrongMethod, Method[] method);
}
