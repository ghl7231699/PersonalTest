package com.example.liangge.rxjavatest.common.utils;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.R;

/**
 * 显示Toast消息
 * 过滤短时间内，大量的重复的toast消息
 */
public class ToastUtils {

    private static Toast mToast = null;
    // 上次显示的msg
    private static String mLastToastMsg = "";
    // 上次toast的时间
    private static long mLastToastTime = System.currentTimeMillis();

    public static Toast init(Context context) {
        if (mToast == null) {
            synchronized (Toast.class) {
                if (mToast == null) {
                    mToast = new Toast(context);
                }
            }
        }
        return mToast;
    }

    /**
     * 外部调用。 显示toast信息
     * 过滤短时间内相同的toast
     */
    public static void toast(String msg) {
        if (TextUtils.isEmpty(msg))
            return;

        if (!check(msg)) {
            return;
        }

        show(msg);
    }

    /**
     * 外部调用。显示toast信息
     * 过滤短时间内相同的toast
     */
    public static void toast(int resStrId) {
        toast(App.getContext().getResources().getString(resStrId));
    }

    /**
     * 是否可以显示本条toast信息
     */
    private static boolean check(String msg) {
        if (msg.equals(mLastToastMsg) && (System.currentTimeMillis() - mLastToastTime) < 2000) {
            return false;
        }
        return true;
    }

    /**
     * 显示toast
     */
    private static void show(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
        mLastToastMsg = msg;
        mLastToastTime = System.currentTimeMillis();
    }

    /**
     * 显示自定义view的toast
     */
    public static void showWidgetView(View view) {
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.show();
    }

    /**
     * toast的取消
     */
    public static void showCancle() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
