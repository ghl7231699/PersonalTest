package com.example.mylibrary;

import java.util.List;

/**
 * Created by guhongliang on 2017/9/19.
 * 运行时权限申请回调接口
 */

public interface PermissionListener {
    /**
     * 授权
     */
    void onGranted();

    /**
     * 权限被拒绝
     */
    void onDenied(List<String> deniedPermission);
}
