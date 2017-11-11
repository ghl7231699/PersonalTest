package com.example.mylibrary;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 简单的日志输出控制类，可通过 loggable 来控制是否输出日志。
 */
public class DLog {

    /**
     * 是否允许输出日志
     */
    public static boolean loggable = true;

    private final static String FILE_DIR_NAME = "Log";
    private final static String FILE_NAME = "HaoZuLog.txt";
    private static Context mContext;

    private static DLog mLog;

    public DLog() {
    }

    public static DLog getInstance() {
        if (mLog == null)
            synchronized (DLog.class) {
                if (mLog == null) {
                    mLog = new DLog();
                }
            }
        return mLog;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static void v(String tag, String msg) {
        if (loggable) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (loggable) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (loggable) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (loggable) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (loggable) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (loggable) {
            Log.e(tag, throwable.getMessage(), throwable);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (loggable) {
            Log.e(tag, msg, throwable);
        }
    }

    /**
     * 日志保存到程序的文件里面并输出日志
     *
     * @param tag
     * @param b
     */
    public static void writeToFile(String tag, Object b) {
        String msg = null;
        if (b == null) {
            return;
        }


        if (loggable && msg != null) {
            if (tag != null) {
                DLog.e(tag, msg);
            }

        }
//            File fileDir = mContext.getDir(FILE_DIR_NAME, Context.MODE_PRIVATE);
        File fileDir = mContext.getCacheDir();
        String filePath = fileDir.getAbsolutePath() + File.separator + FILE_NAME;
        try {
            FileUtils.saveDataLocal(filePath, fileDir, msg);
        } catch (IOException e) {
            DLog.e(DLog.class.getSimpleName(), collectCrashMsg(e));
        }
    }

    /**
     * 将日志写到SD卡里面，同时输出log日志
     *
     * @param tag
     * @param b
     */
    public static void writeToSDCard(String tag, Object b) {

        String msg = null;
        if (b == null) {
            return;
        }

        if (b instanceof Exception) {
            msg = collectCrashMsg((Exception) b);
        }

        if (loggable && msg != null) {
            if (tag != null) {
                DLog.e(tag, msg);
            }

        }

        FileWriter fw = null;
        try {
            File fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + FILE_DIR_NAME);
//            if (!file.exists()) {
//                file.createNewFile();
//            } else if (file.length() > 1024 * 1024) {
//                file.delete();
//                file.createNewFile();
//            }
//            fw = new FileWriter(file, true);
//            fw.write(TimeUtil.getTimeStamp() + " : " + msg + "\r\n");
            String filePath = fileDir.getAbsolutePath() + File.separator + FILE_NAME;
            FileUtils.saveDataLocal(filePath, fileDir, msg);
        } catch (Exception e) {
            DLog.e(DLog.class.getSimpleName(), collectCrashMsg(e));
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 收集奔溃信息
     *
     * @param b
     */
    private static String collectCrashMsg(Exception b) {
        StringBuilder sb = new StringBuilder();

        if (b == null) {
            return null;
        }
        sb.append(TimeUtil.getTimeStamp() + "\n");
//        if (b instanceof Exception) {
//            sb.append(((Exception) b).getMessage() + "\n");

//        } else if (b instanceof Throwable) {
        sb.append(((Throwable) b).getMessage() + "\n");
//        }
        StackTraceElement[] stackTrace = b.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            sb.append(stackTrace[i].toString() + "\n");
        }
        return new String(sb);
    }
}
