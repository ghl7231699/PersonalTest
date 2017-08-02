package com.example.liangge.rxjavatest.presenter;

import android.Manifest;
import android.app.Activity;

import com.example.liangge.rxjavatest.bean.UserInfo;
import com.example.liangge.rxjavatest.common.constant.Data;
import com.example.liangge.rxjavatest.common.constant.Header;
import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.rx.RxHttpTransFormer;
import com.example.liangge.rxjavatest.data.UserInfoModule;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by guhongliang on 2017/8/2.
 */

public class RxjavaPresenter extends BasePresenter<UserInfoModule, UserInfoContract.RxView> {
    @Inject
    public RxjavaPresenter(UserInfoModule userInfoModule, UserInfoContract.RxView view) {
        super(userInfoModule, view);
    }

    private UserParam getUserParam() {
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "ebt-003");
        Data data = new Data("19680106", "17205261");
        UserParam param = new UserParam();
        param.setHeader(header);
        param.setUserData(data);
        return param;
    }

    public void create() {
        mV.showUserInfo(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("亮哥：刘东，你是猪吗" + "\n");
                e.onNext("刘东：我确实是个猪" + "\n");
                e.onComplete();
                e.onNext("亮哥：你个山炮" + "\n");
            }
        }), 1);
    }

    public void map() {

        mV.showUserInfo(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }), 2);
    }

    public void flatMap() {
        RxPermissions permissions = new RxPermissions((Activity) mV);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .flatMap(new Function<Boolean, ObservableSource<UserInfo>>() {
                    @Override
                    public ObservableSource<UserInfo> apply(@NonNull Boolean aBoolean) throws Exception {

                        if (aBoolean) {
                            return mM.LoadUserInfo(getUserParam())
                                    .compose(RxHttpTransFormer.<UserInfo>handleResult());
                        } else
                            mV.disMissLoading();
                        mV.showError("拒绝权限，无法进行正常操作");
                        return Observable.empty();
                    }
                }).subscribe(new Consumer<UserInfo>() {
            @Override
            public void accept(@NonNull UserInfo userInfo) throws Exception {
                if (userInfo != null) {
                    mV.showUserInfo(userInfo, 3);
                }
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mV.showError(throwable.toString());
            }
        });
    }

}
