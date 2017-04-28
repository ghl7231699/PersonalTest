package com.example.liangge.rxjavatest.common.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * 类名称：DownLoadFileCallBack
 * 类描述：下载文件工具类
 * 创建人：ghl
 * 创建时间：2017/4/13 16:25
 * 修改人：ghl
 * 修改时间：2017/4/13 16:25
 *
 * @version v1.0
 */

public class DownLoadFileCallBack extends FileCallback {
    private UserInfoContract.View mView;
    private Activity mContext;
    private String mString;
//    private AddProductContract.View views;

//    public DownLoadFileCallBack(@NonNull String destFileDir, @NonNull String destFileName, AddProductContract.View view, String s) {
//        super(destFileDir, destFileName);
//        this.views = view;
//        this.mString = s;
//        if (view instanceof Activity) {
//            this.mContext = (Activity) views;
//        } else if (view instanceof Fragment) {
//            this.mContext = ((Fragment) views).getActivity();
//        }
//    }

    public DownLoadFileCallBack(@NonNull String destFileDir, @NonNull String destFileName, UserInfoContract.View view, String s) {
        super(destFileDir, destFileName);
        this.mView = view;
        this.mString = s;
        if (view instanceof Activity) {
            this.mContext = (Activity) mView;
        } else if (view instanceof Fragment) {
            this.mContext = ((Fragment) mView).getActivity();
        }
    }

    //提示下载中
    @Override
    public void onBefore(BaseRequest request) {
        mView.showLoading();
    }

    //下载完成
    @Override
    public void onResponse(boolean isFromCache, File file, Request request, @Nullable Response response) {
        Log.d(TAG, "onResponse: 下载完成");
        mView.disMissLoading();
        if (FileUtil.fileExits(mString)) {
            mView.loadDetailContent(new File(mString), mString);
        } else {
            mView.showError("本地没数据");
            mContext.finish();
        }
    }

    //下载
    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        Log.d(TAG, "downloadProgress: 正在下载");
    }


    //下载出错
    @Override
    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
        super.onError(isFromCache, call, response, e);
        mView.showError("下载失败了，请检查您的网络是否连接正常");
        mContext.finish();
    }
}
