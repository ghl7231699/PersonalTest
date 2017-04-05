package com.example.liangge.rxjavatest;

import android.app.Application;
import android.content.Context;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class App extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
