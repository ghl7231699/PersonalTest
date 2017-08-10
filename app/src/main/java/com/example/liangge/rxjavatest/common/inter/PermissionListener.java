package com.example.liangge.rxjavatest.common.inter;

/**
 * 类名称：PermissionListener
 * 类描述：权限处理接口
 * 创建人：ghl
 * 创建时间：2017/4/28 14:42
 * 修改人：ghl
 * 修改时间：2017/4/28 14:42
 *
 * @version v1.0
 */
public interface PermissionListener {
    void onGranted();// 同意权限

    void onDenied();// 拒绝权限并返回
}
