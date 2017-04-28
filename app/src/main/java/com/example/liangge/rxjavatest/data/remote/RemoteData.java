package com.example.liangge.rxjavatest.data.remote;


import com.example.liangge.rxjavatest.bean.UserInfo;
import com.example.liangge.rxjavatest.common.rx.RxHttpTransFormer;
import com.example.liangge.rxjavatest.data.http.Api;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by guhongliang on 2017/4/21.
 * 获取网络数据
 */

public class RemoteData {
    private String s;
    private Api mService;

    public RemoteData(String s, Api service) {
        this.s = s;
        this.mService = service;
    }

    public void loadRemoteData() {
        mService.checkIsUpdate()
                .compose(RxHttpTransFormer.<UserInfo>handleResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(@NonNull UserInfo userInfo) throws Exception {
                        if (userInfo != null) {
                            //有更新，进行下载
//                    OkHttpUtils.get("")//
//                            .tag(this)//
//                            .execute(new DownloadFileCallBack(Environment.getExternalStorageDirectory() +
//                                    "/temp", "qcl.pdf"));
                        }
                    }
                });
    }
}
