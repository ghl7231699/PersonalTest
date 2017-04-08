package com.example.liangge.rxjavatest.di.component;

import com.example.liangge.rxjavatest.ui.activity.DaggerActivity;
import com.example.liangge.rxjavatest.ui.activity.UserInfoActivity;
import com.example.liangge.rxjavatest.di.modules.UserModules;

import dagger.Component;

/**
 * Created by guhongliang on 2017/3/30.
 */
@Component(modules = {UserModules.class})
public interface UserComponent {

    void inject(DaggerActivity activity);

    void inject(UserInfoActivity activity);
}
