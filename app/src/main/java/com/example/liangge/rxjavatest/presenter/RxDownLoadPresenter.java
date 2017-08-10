package com.example.liangge.rxjavatest.presenter;

import android.Manifest;
import android.app.Activity;

import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.common.inter.PermissionListener;
import com.example.liangge.rxjavatest.common.utils.PermissionUtil;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.data.RxDownListModule;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by guhongliang on 2017/5/8.
 */

public class RxDownLoadPresenter extends BasePresenter<RxDownListModule, UserInfoContract.View> {
    @Inject
    public RxDownLoadPresenter(RxDownListModule rxDownListModule, UserInfoContract.View view) {
        super(rxDownListModule, view);
    }


    public void showDownList() {
        PermissionUtil.requestPermission((Activity) mV, new PermissionListener() {
            @Override
            public void onGranted() {
                mM.getData()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Fruit>>() {
                            @Override
                            public void accept(@NonNull List<Fruit> fruits) throws Exception {
                                mV.showUserInfo(fruits);
                            }
                        });
            }

            @Override
            public void onDenied() {
                ToastUtils.toast("权限被拒绝了。。。。。。。。。。。。。");
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }
}
