package com.example.liangge.rxjavatest;

import android.app.Application;
import android.content.Context;

import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.di.component.DaggerAppComponent;
import com.example.liangge.rxjavatest.di.modules.AppModule;
import com.example.liangge.rxjavatest.di.modules.HttpModule;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class App extends Application {
    private static Context mContext;
    private AppComponent mAppComponent;
    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).httpModule(new HttpModule()).build();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Application getApplication() {
        return mApp;
    }
}
