package com.example.mylibrary;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2017/9/19.
 * Activity的管理类
 */

public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<>();

    /**
     * 添加当前Activity入栈
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除当前Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public static Activity getTopActivity() {
        if (activityList.isEmpty()) {
            return null;
        }
        return activityList.get(activityList.size() - 1);
    }

}
