package com.example.liangge.rxjavatest.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by guhongliang on 2017/8/26.
 */

public class PluginManager {
    private DexClassLoader mLoader;
    private Resources mResources;
    private Context mContext;
    private String entryName;

    public String getEntryName() {
        return entryName;
    }

    public Resources getResources() {
        return mResources;
    }

    public DexClassLoader getLoader() {
        return mLoader;
    }

    private static final PluginManager ourInstance = new PluginManager();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }

    public void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    public void loadPath(String path) {
        File dexFile = mContext.getDir("dex", Context.MODE_PRIVATE);
        mLoader = new DexClassLoader(path, dexFile.getAbsolutePath(), null, mContext.getClassLoader());
        //实例化Resource
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        entryName = packageArchiveInfo.activities[0].name;
        /**
         *     public Resources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
         this(null);
         mResourcesImpl = new ResourcesImpl(assets, metrics, config, new DisplayAdjustments());
         }
         */
        try {
            AssetManager manager = AssetManager.class.newInstance();
            try {
                Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
                mResources = new Resources(manager, mContext.getResources().getDisplayMetrics()
                        , mContext.getResources().getConfiguration());
                addAssetPath.invoke(manager, path);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
