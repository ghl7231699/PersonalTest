package com.example.statisticsclickmodule;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 类名称：ClassUtils
 * 类描述：信息采集类
 * 创建人：ghl
 * 创建时间：2017/9/12 下午2:10
 * 修改人：ghl
 * 修改时间：2017/9/12 下午2:10
 *
 * @version v1.0
 */

public class ClassUtils {
    public static final String TAG = "ClassUtils";

    public static String getCurrentActivity(Object o) {
        String className = o.getClass().getName();
        Log.e(TAG, "getClassName: " + className);
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
}
