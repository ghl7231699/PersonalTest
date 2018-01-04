package com.example.liangge.rxjavatest;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.di.component.DaggerAppComponent;
import com.example.liangge.rxjavatest.di.modules.AppModule;
import com.example.liangge.rxjavatest.di.modules.HttpModule;
import com.example.liangge.rxjavatest.greendao.gen.DaoMaster;
import com.example.liangge.rxjavatest.greendao.gen.DaoSession;
import com.example.liangge.rxjavatest.ndk.AndFixPatchManager;
import com.example.mylibrary.CrashHandler;
import com.example.mylibrary.DLog;
import com.lzy.okhttputils.OkHttpUtils;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class App extends TinkerApplication {
    private static Context mContext;
    private AppComponent mAppComponent;
    public static App mApp;
    private static String LOCAL_DATA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DownLoad";
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.liangge.rxjavatest.ThinkApplicationLink");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).httpModule(new HttpModule()).build();
        mContext = getApplicationContext();
        OkHttpUtils.init(this);
        DLog.init(this);
        //初始化奔溃处理类
        CrashHandler.getInstance().init(this);
        //初始化andFix
        AndFixPatchManager.getInstance().init(this);
        //初始化Toast
        ToastUtils.init(mApp);
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

    public static final String getLocalDataPath() {
        return LOCAL_DATA_PATH;
    }

    /**
     * 设置greenDao
     */
    private void setDataBase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
}
