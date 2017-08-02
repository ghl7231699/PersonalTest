package com.example.liangge.rxjavatest.di.modules;

import android.util.Log;

import com.example.liangge.rxjavatest.common.constant.ApiService;
import com.example.liangge.rxjavatest.common.constant.UserManager;
import com.example.liangge.rxjavatest.common.constant.UserStore;
import com.example.liangge.rxjavatest.data.RxDownListModule;
import com.example.liangge.rxjavatest.data.UserInfoModule;
import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;

import dagger.Module;
import dagger.Provides;


/**
 * Created by guhongliang on 2017/3/30.
 */
@Module
public class UserModules {
    private UserInfoContract.View mView;
    private UserInfoContract.RxView mRxView;

    public UserModules(UserInfoContract.View view) {
        mView = view;
    }

    public UserModules(UserInfoContract.RxView rxView) {
        mRxView = rxView;
    }

    @Provides
    public String provideUrl() {
        return "刘东，你是猪吗";
    }

    @Provides
    public UserManager provideUserManager(ApiService apiService, UserStore s) {
        Log.d("UserModules", "provideUserManager: ");
        return new UserManager(apiService, s);
    }

    //    @Provides
//    public UserInfoContract.Presenter providePresenter(UserInfoContract.View view, Api api) {
//        return new LoginPresenter(view, api);
//    }
    @Provides
    public UserInfoModule provideUserInfoModel(Api api) {
        return new UserInfoModule(api);
    }

    @Provides
    public UserInfoContract.View providerView() {
        return mView;
    }

    @Provides
    public UserInfoContract.RxView providerRxView() {
        return mRxView;
    }

    @Provides
    public RxDownListModule provideRxDownListModule(Api api) {
        return new RxDownListModule(api);
    }
}
