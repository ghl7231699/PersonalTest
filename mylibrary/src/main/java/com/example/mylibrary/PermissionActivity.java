package com.example.mylibrary;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by guhongliang on 2017/9/19.
 */

public abstract class PermissionActivity extends AppCompatActivity {
    private static PermissionListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityCollector.addActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedList = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            deniedList.add(permissions[i]);
                        }
                    }
                    if (deniedList.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedList);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 处理运行时权限
     *
     * @param permissions
     * @param listener
     */
    public static void onRequestPermissionResult(String[] permissions, PermissionListener listener) {
        mListener = listener;
        //存放被拒绝的权限
        List<String> permissionList = new ArrayList<>();
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            return;
        }
        for (String p :
                permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, p) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(p);
                mListener.onDenied(permissionList);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }


    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
