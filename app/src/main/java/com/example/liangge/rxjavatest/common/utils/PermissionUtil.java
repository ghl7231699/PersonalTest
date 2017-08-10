package com.example.liangge.rxjavatest.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.example.liangge.rxjavatest.common.inter.PermissionListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by guhongliang on 2017/4/19.
 * 运行时权限工具类
 */

public class PermissionUtil {
    public static void requestPermission(final Activity activity, final PermissionListener listener, String... permission) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.requestEach(permission)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {

                        if (permission.granted) {//用户同意授权
                            listener.onGranted();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //用户不同意授权，但没有选中不在询问
                            listener.onDenied();
                        } else {
                            //用户不同意授权，选择了不在询问
                            openSetting(activity);
                        }
                    }
                });

    }

    /**
     * 打开权限管理界面
     *
     * @param activity
     */
    private static void openSetting(final Activity activity) {
        new AlertDialog.Builder(activity).setTitle("警告")
                .setMessage("如果您不授权，将无法进行正常的操作！")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "com.nciebt.zyj", null);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, 11111);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
