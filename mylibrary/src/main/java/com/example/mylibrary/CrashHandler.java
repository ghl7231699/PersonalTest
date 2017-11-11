package com.example.mylibrary;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by guhongliang on 2017/9/25.
 */

public class CrashHandler implements UncaughtExceptionHandler {
    private static String TAG = CrashHandler.class.getSimpleName();
    //系统默认的处理UncaughtExceptionHandler处理类
    private UncaughtExceptionHandler mExceptionHandler;

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;

    /**
     * 保证只有一个crashHandler对象
     */
    public CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        boolean b = handlerException(throwable);
        if (!b && mExceptionHandler != null) {
            mExceptionHandler.uncaughtException(thread, throwable);
        } else {
            SystemClock.sleep(2000);
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 作者：ghl
     * 描述：初始化
     * 创建时间：2017/9/25 下午3:47
     *
     * @Params:
     * @Return:
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtExceptionHandler
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * 作者：ghl
     * 描述：自定义处理unCaught异常
     * 创建时间：2017/9/25 下午4:12
     *
     * @Params:
     * @Return:
     */
    private boolean handlerException(Throwable t) {
        if (t == null) {
            return false;
        }

        try {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "很抱歉,程序出现异常,请重启.",
                            Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            //收集错误信息，保存到本地并输出日志
//            DLog.writeToFile(CrashHandler.class.getSimpleName(), t);
            DLog.writeToSDCard(TAG, t);
            SystemClock.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
