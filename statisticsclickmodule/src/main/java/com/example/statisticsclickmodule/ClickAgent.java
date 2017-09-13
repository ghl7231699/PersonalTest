package com.example.statisticsclickmodule;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guhongliang on 2017/9/12.
 */

public class ClickAgent {
    private static ClickAgent mAgent;
    private static Map<String, String> mMap = new HashMap<>();

    public static final String TAG = "ClassUtils";
    private static String packageName;

    public static ClickAgent getInstance() {
        if (mAgent == null) {
            synchronized (ClickAgent.class) {
                if (mAgent == null) {
                    mAgent = new ClickAgent();
                }
            }
        }
        return mAgent;
    }

    /**
     * 初始化操作
     *
     * @param context
     */
    public static void init(Context context) {
        if (context == null) {
            return;
        }
        packageName = context.getPackageName();
        Log.e(TAG, "init: " + packageName);
    }

    public static String getCurrentActivity(Object o) {
        String className = o.getClass().getName();
        Log.e(TAG, "getClassName: " + className);
        mMap.put(packageName, className);
        return className == null ? null : className;
    }

    public static String getDeviceId(Context context) {
        //IMEI(imei)
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceId = tm.getImei();
        } else {
            deviceId = tm.getDeviceId();
        }
        Log.e(TAG, "getDeviceId: " + deviceId);
        return deviceId != null ? deviceId : null;
    }

//
//    public void  test(){
//        if (TextUtils.isEmpty(informationLk) || infomationLd == null) {
//            baseViewHolder.setVisible(R.id.ld_tv, false);
//            baseViewHolder.setVisible(R.id.lk_tv, false);
//            baseViewHolder.setTextColor(R.id.tvCalActual, Color.parseColor("#9B9B9B"));
//        }
//        if (!TextUtils.isEmpty(informationLk) || infomationLd != null) {
//            baseViewHolder.setVisible(R.id.lk_tv, true);
//            baseViewHolder.setVisible(R.id.ld_tv, true);
//            baseViewHolder.setText(R.id.lk_tv, informationLk);
//            baseViewHolder.setText(R.id.ld_tv, infomationLd);
//            baseViewHolder.setTextColor(R.id.tvCalActual, Color.parseColor("#d29000"));
//        }
//
//
//
//        if (!TextUtils.isEmpty(informationLk) || infomationLd == null) {
//            baseViewHolder.setVisible(R.id.lk_tv, true);
//            baseViewHolder.setText(R.id.lk_tv, informationLk);
//            baseViewHolder.setTextColor(R.id.tvCalActual, Color.parseColor("#13a419"));
//            baseViewHolder.setVisible(R.id.ld_tv, false);
//        }
//        if (TextUtils.isEmpty(informationLk) || infomationLd != null) {
//            baseViewHolder.setVisible(R.id.ld_tv, true);
//            baseViewHolder.setText(R.id.ld_tv, infomationLd);
//            baseViewHolder.setTextColor(R.id.tvCalActual, Color.parseColor("#f44336"));
//            baseViewHolder.setVisible(R.id.lk_tv, false);
//
//        }
//    }
}
