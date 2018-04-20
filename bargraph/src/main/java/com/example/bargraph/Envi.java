/**
 * 
 */
package com.example.bargraph;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;
import java.util.List;

/**
 * 包含程序运行时的部分信息，如程序版本号，设备分辨率等。该类不应包含配置信息，如渠道号等。
 */
public class Envi {
    /**
     * 当前应用程序的 Application Context
     */
    public static Application appContext;
    /**
     * 当前应用程序包名，默认赶集生活的包名。
     */
    public static String packageName = "com.ganji.android";
    /**
     * 当前的版本号，比如：6.0.0
     */
    public static String versionName;
    /**
     * 当前的 versionCode，比如：250
     */
    public static int versionCode;
    /**
     * 当前是否处于 debug 模式。非正式签名时处于 debug 模式，否则处于非 debug 模式。
     */
    public static boolean debuggable;
    /**
     * 应用程序在系统中的用户id
     */
    public static int uid;
    /**
     * 系统的 User-Agent
     */
    public static String systemUserAgent;
    /**
     * 当前设备的屏幕宽度（短边）所占有的像素数
     */
    public static int screenWidth;
    /**
     * 当前设备的屏幕高度（长边）所占有的像素数
     */
    public static int screenHeight;
    /**
     * 屏幕的密度参数，如：0.75, 1.0, 1.5, 2.0, 3.0 等
     */
    public static float density;
    /**
     * 设备的型号
     */
    public static String model;
    /**
     * 设备型号、屏幕分辨率、屏幕密度信息，如：GT-I9500#1080*1920#3.0#4.4.2
     */
    public static String clientAgent;
    /**
     * 当前进程的名字
     */
    public static String processName;
    /**
     * 当前设备的 Mac 地址
     */
    public static String MAC;
    /**
     * 当前设备的 IMEI
     */
    public static String IMEI;
    /**
     * 当前的 UI 线程
     */
    public static Thread uiThread;
    /**
     * 该对象在应用程序启动的时候在启动界面（如 LauncherActivity ）创建，
     * 用于标识当前进程会话，如果该对象丢失，则应重启应用程序，或者重新做初始化， 以防止因应用程序的某些静态对象丢失而导致 crash
     */
    public static Object processGuarder;
    /**登录用户Token**/
//    public static String token;

    /**
     * 初始化环境变量
     * 
     * @param app 当前应用程序的 Application Context
     */
    @SuppressLint("MissingPermission")
    public static void initialize(Application app) {
        appContext = app;
        uiThread = Thread.currentThread();
        processName = getCurrentProcessName();
        
        try {
            PackageInfo pi = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            packageName = pi.packageName;
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            debuggable = (pi.applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            uid = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), 0).uid;
            systemUserAgent = System.getProperty("http.agent");
        } catch (Exception e) {
        }
        
        try {
            WifiManager wifi = (WifiManager) Envi.appContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            MAC = info.getMacAddress();
        } catch (Exception e) {
        }
        
        try {
            IMEI = ((TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
        }
        
        DisplayMetrics displayMetrics = appContext.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        density = displayMetrics.density;
        
        model = Build.MANUFACTURER + "/" + Build.MODEL;
        clientAgent = Build.MANUFACTURER + "/" + Build.MODEL + "#" + screenWidth + "*" + screenHeight +
                "#" + density + "#" + Build.VERSION.RELEASE;
    }

    public static void print() {
        // @formatter:off
        StringBuilder sb = new StringBuilder();
        sb.append("---------- environment ----------\n")
                .append("packageName: ").append(packageName).append("\n")
                .append("versionName: ").append(versionName).append("\n")
                .append("versionCode: ").append(versionCode).append("\n")
                .append("debuggable: ").append(debuggable).append("\n")
                .append("model: ").append(model).append("\n")
                .append("systemUserAgent: ").append(systemUserAgent).append("\n")
                .append("screenWidth: ").append(screenWidth).append("\n")
                .append("screenHeight: ").append(screenHeight).append("\n")
                .append("density: ").append(density).append("\n")
                .append("MAC: ").append(MAC).append("\n")
                .append("IMEI: ").append(IMEI).append("\n")
                .append("processName: ").append(processName).append("\n")
                .append("uid: ").append(uid).append("\n")
                .append("clientAgent: ").append(clientAgent).append("\n")
                .append("---------------------------------");
        // @formatter:on
    }
    
    private static String getCurrentProcessName() {
        ActivityManager am = (ActivityManager) Envi.appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
        int pid = Process.myPid();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).pid == pid) {
                    return list.get(i).processName;
                }
            }
        }
        return null;
    }



    /**
     * 安装APK
     * @param context
     * @param file
     */
    public static void installApk(Context context, String file) {
        File apkFile = new File(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    /**
     * 卸载APP
     * @param context
     * @param packageName 包名
     */
    public static void uninstallAPK(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }
}
