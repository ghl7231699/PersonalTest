package com.example.liangge.rxjavatest.di.modules;

import android.util.Log;

import com.example.liangge.rxjavatest.common.constant.ApiService;
import com.example.liangge.rxjavatest.common.constant.UserManager;
import com.example.liangge.rxjavatest.common.constant.UserStore;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.example.liangge.rxjavatest.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;


/**
 * Created by guhongliang on 2017/3/30.
 */
@Module
public class UserModules {
    //    @Provides
//    public ApiService provideApiService() {
//        Log.d("UserModules", "provideApiService: ");
//        return new ApiService();
//    }
    private UserInfoContract.View mView;

    public UserModules(UserInfoContract.View view) {
        mView = view;
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

    @Provides
    public UserInfoContract.Presenter providePresenter(UserInfoContract.View view) {
        return new LoginPresenter(view);
    }

    @Provides
    public UserInfoContract.View providerView() {
        return mView;
    }
}
