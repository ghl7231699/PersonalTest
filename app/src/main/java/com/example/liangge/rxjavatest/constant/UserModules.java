package com.example.liangge.rxjavatest.constant;

import android.util.Log;

import com.example.liangge.rxjavatest.common.constant.ApiService;
import com.example.liangge.rxjavatest.common.constant.UserManager;
import com.example.liangge.rxjavatest.common.constant.UserStore;
import com.example.liangge.rxjavatest.data.UserInfoModel;
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

    //    @Provides
//    public ApiService provideApiService() {
//        Log.d("UserModules", "provideApiService: ");
//        return new ApiService();
//    }
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
    public UserInfoModel provideUserInfoModel(Api api) {
        return new UserInfoModel(api);
    }

    @Provides
    public UserInfoContract.View providerView() {
        return mView;
    }
}
