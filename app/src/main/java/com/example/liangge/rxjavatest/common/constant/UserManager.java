package com.example.liangge.rxjavatest.common.constant;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class UserManager {
    private ApiService mApiService;
    private UserStore mUserStore;

    public UserManager(ApiService mApiService, UserStore mUserStore) {
        this.mApiService = mApiService;
        this.mUserStore = mUserStore;
    }

    public void register() {
        mApiService.register();
        mUserStore.register();
    }
}
