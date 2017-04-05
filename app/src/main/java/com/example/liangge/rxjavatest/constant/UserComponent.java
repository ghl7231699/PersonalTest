package com.example.liangge.rxjavatest.constant;

import com.example.liangge.rxjavatest.activity.DaggerActivity;

import dagger.Component;

/**
 * Created by guhongliang on 2017/3/30.
 */
@Component(modules = {UserModules.class})
public interface UserComponent {

    void inject(DaggerActivity activity);

}
