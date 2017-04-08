package com.example.liangge.rxjavatest.di.modules;

import android.app.Application;

import com.example.liangge.rxjavatest.App;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liangge on 2017/4/8.
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Application providerApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    public Gson providerGson() {
        return new Gson();
    }
}
