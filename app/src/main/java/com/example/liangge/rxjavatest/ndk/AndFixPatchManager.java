package com.example.liangge.rxjavatest.ndk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by ghl11 on 2017/11/14.
 */

public class AndFixPatchManager {
    private static AndFixPatchManager manager = new AndFixPatchManager();
    private static PatchManager mPatchManager = null;

    public static AndFixPatchManager getInstance() {
        return manager;
    }

    /**
     * 初始化andFix
     *
     * @param context
     */
    public void init(Context context) {
        mPatchManager = new PatchManager(context);
        mPatchManager.init(getVersionName(context));//current version
        mPatchManager.loadPatch();
    }

    /**
     * 加载我们的patch文件
     *
     * @param path
     */
    public static void addPatch(String path) {
        try {
            if (mPatchManager != null) {
                mPatchManager.addPatch(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
