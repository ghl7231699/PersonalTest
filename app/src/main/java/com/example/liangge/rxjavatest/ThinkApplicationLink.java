package com.example.liangge.rxjavatest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.example.liangge.rxjavatest.thinker.ThinkerManger;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * Created by guhongliang on 2017/11/25.
 */
@SuppressWarnings("unused")
public class ThinkApplicationLink extends DefaultApplicationLike {
    public ThinkApplicationLink(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        //
        ThinkerManger.installTinker(this);
    }
}
