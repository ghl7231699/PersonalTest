package com.example.liangge.rxjavatest.constant;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class ApiService {
    public ApiService() {
        Log.d("ApiService", "ApiService: ");
    }

    @Inject
    public ApiService(String url) {
        Log.d("ApiService", "register: " + url);
    }

    public void register() {
        Log.d("ApiService", "register:执行了 ");
    }
}
