package com.example.liangge.rxjavatest.constant;

import android.util.Log;

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
    @Provides
    public String provideUrl(){
        return  "刘东，你是猪吗";
    }
    @Provides
    public UserManager provideUserManager(ApiService apiService, UserStore s) {
        Log.d("UserModules", "provideUserManager: ");
        return new UserManager(apiService, s);
    }
}
