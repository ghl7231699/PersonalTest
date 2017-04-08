package com.example.liangge.rxjavatest.common.utils;

import com.example.liangge.rxjavatest.di.data.Api;
import com.example.liangge.rxjavatest.common.constant.SslContextFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guhongliang on 2017/3/30.
 * Retrofit工具类
 */

public class RetrofitUtil {
    public static int DEFAULT_TIMEOUT = 5;
    private Api mApi;
    private static RetrofitUtil mInstance;

    //    public static RetrofitUtil getInstance() {
//        if (mInstance == null) {
//            synchronized (RetrofitUtil.class) {
//                if (mInstance == null) {
//                    mInstance = new RetrofitUtil();
//                }
//            }
//        }
//        return mInstance;
//    }
    public static Api retrofitInitialize(String url) {
        OkHttpClient client = SslContextFactory.getOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(url)
                .build();
        return retrofit.create(Api.class);
    }
}
