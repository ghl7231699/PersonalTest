package com.example.liangge.rxjavatest.di.component;

import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.di.modules.AppModule;
import com.example.liangge.rxjavatest.di.modules.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by liangge on 2017/4/8.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    Api getApi();
}
