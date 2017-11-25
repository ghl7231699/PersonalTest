package com.example.liangge.rxjavatest.thinker;

import android.content.Context;
import android.os.Build;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by guhongliang on 2017/11/25.
 */

public class ThinkerManger {
    private static ApplicationLike applicationLike;
    private static boolean isInstalled;

    public static void setTinkerApplicationLike(ApplicationLike appLike) {
        applicationLike = appLike;
    }


    /**
     * you can specify all class you want.
     * sometimes, you can only install tinker in some process you want!
     *
     * @param appLike
     */
    public static void installTinker(ApplicationLike appLike) {
        if (isInstalled) {
            return;
        }
//        //or you can just use DefaultLoadReporter
//        LoadReporter loadReporter = new SampleLoadReporter(appLike.getApplication());
//        //or you can just use DefaultPatchReporter
//        PatchReporter patchReporter = new SamplePatchReporter(appLike.getApplication());
//        //or you can just use DefaultPatchListener
//        PatchListener patchListener = new SamplePatchListener(appLike.getApplication());
//        //you can set your own upgrade patch if you need
//        AbstractPatch upgradePatchProcessor = new UpgradePatch();

//        TinkerInstaller.install(appLike,
//                loadReporter, patchReporter, patchListener,
//                SampleResultService.class, upgradePatchProcessor);
        TinkerInstaller.install(appLike);

        isInstalled = true;

    }

    public static void loadPatch(String patch) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getContexts(), patch);
        }
    }

    private static Context getContexts() {
        if (applicationLike != null) {
            return applicationLike.getApplication().getApplicationContext();
        }
        return null;
    }
}
