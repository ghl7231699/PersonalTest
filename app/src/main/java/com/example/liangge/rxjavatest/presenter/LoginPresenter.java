package com.example.liangge.rxjavatest.presenter;

import android.util.Log;

import com.example.liangge.rxjavatest.common.constant.Data;
import com.example.liangge.rxjavatest.common.constant.Header;
import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.utils.BeanTest;
import com.example.liangge.rxjavatest.data.UserInfoModel;
import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.di.modules.UserModules;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guhongliang on 2017/4/6.
 */

public class LoginPresenter extends BasePresenter<UserInfoModel,UserInfoContract.View> {
//    private Api mApi;
//    private UserInfoContract.View mView;
    @Inject
    public LoginPresenter(UserInfoModel userModules, UserInfoContract.View view) {
        super(userModules, view);
    }


//    public LoginPresenter(UserInfoContract.View view, Api api) {
//        mView = view;
//        this.mApi = api;
//        view.setPresenter(this);
//    }

//    @Override
//    public void start() {
//        LoadUserInfo();
//    }

    public void LoadUserInfo() {
        mV.showLoading();
//        mM.LoadUserInfo(getUserParam(),mV);
        mM.LoadUserInfo(getUserParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BeanTest>() {
                    @Override
                    public void accept(@NonNull BeanTest beanTest) throws Exception {
                        if (beanTest != null) {
                            String s = new Gson().toJson(beanTest);
                            Log.d("MapActivity", "username= " + s);
                            mV.showUserInfo(beanTest);
                            mV.disMissLoading();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mV.disMissLoading();
                        mV.showError(throwable.getMessage());
                    }
                });
    }

    private UserParam getUserParam() {
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "ebt-003");
        Data data = new Data("19680106", "17205261");
        UserParam param = new UserParam();
        param.setHeader(header);
        param.setUserData(data);
        return param;
    }
}
