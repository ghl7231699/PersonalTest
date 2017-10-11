package com.example.statisticsclickmodule;

import android.app.Application;

import com.example.mylibrary.CrashHandler;
import com.example.mylibrary.DLog;

/**
 * Created by guhongliang on 2017/9/25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        CrashHandler.getInstance().init(this);
        DLog.init(this);
        super.onCreate();
    }
}
