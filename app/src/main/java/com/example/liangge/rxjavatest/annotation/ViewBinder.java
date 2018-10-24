package com.example.liangge.rxjavatest.annotation;

import android.app.Activity;

import com.example.liangge.rxjavatest.R;

/**
 * Created by guhongliang on 2018/10/16.
 */

public class ViewBinder {
    private static void bindActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        Class<?> aClass = activity.getClass();

        BindLayout bindLayout = aClass.getAnnotation(BindLayout.class);
        if (bindLayout != null) {
            int layout = bindLayout.layout();
            if (layout != 0) {
                activity.setContentView(layout);

            }
        }

    }
}
