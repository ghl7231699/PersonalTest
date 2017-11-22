package com.example.liangge.rxjavatest.ndk.CustomFix;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.alipay.euler.andfix.annotation.MethodReplace;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by guhongliang on 2017/11/18.
 */

public class FixManager {
    public static final String TAG = FixManager.class.getSimpleName();
    private Context mContext;
    private dalvik.system.DexFile dexFile;

    public FixManager(Context context) {
        mContext = context;
        HandlerNative.init(Build.VERSION.SDK_INT);//JNI需要进行版本校验，区分虚拟机的类型
    }

    public void fix(File file, final ClassLoader classLoader, List<String> list) {
        File patchFile = new File(mContext.getFilesDir(), file.getName());
        if (patchFile.exists()) {
            patchFile.delete();
        }
        try {
            dexFile = dalvik.system.DexFile.loadDex(file.getAbsolutePath(), patchFile.getAbsolutePath(), Context.MODE_PRIVATE).loadDex(file.getAbsolutePath(), patchFile.getAbsolutePath(), Context.MODE_PRIVATE);
            ClassLoader classLoader1 = new ClassLoader() {
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    Class aClass = dexFile.loadClass(name, this);
                    if (aClass == null) {
                        aClass = Class.forName(name);
                    }
                    return aClass;
                }

                @Override
                protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                    return super.loadClass(name, resolve);
                }
            };
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String key = entries.nextElement();
                if (!list.contains(key)) {
                    continue;
                }
                Class realClass = dexFile.loadClass(key, classLoader1);
                if (realClass != null) {
                    fixClass(realClass, classLoader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixClass(Class realClass, ClassLoader classLoader) {
        Method[] methods = realClass.getMethods();
        for (Method method : methods
                ) {
            MethodReplace methodReplace = method.getAnnotation(MethodReplace.class);
            if (methodReplace == null) {
                continue;
            }
            Log.i(TAG, "找到替换方法   " + methodReplace.toString() + "  clazz 对象  " + realClass.toString());
            String clazz=methodReplace.clazz();
            String methodName = methodReplace.method();
            replaceMethod(classLoader,clazz,methodName,realClass,method);
        }
    }

    private void replaceMethod(ClassLoader classLoader, String clazz, String methodName, Class realClass, Method method) {
    }
}
