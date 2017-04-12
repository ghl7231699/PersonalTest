package com.example.liangge.rxjavatest.constant;

import com.example.liangge.rxjavatest.di.ViewScope;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.UserInfoActivity;

import dagger.Component;

/**
 * Created by guhongliang on 2017/3/30.
 */
@ViewScope
@Component(modules = UserModules.class, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(UserInfoActivity activity);
}
