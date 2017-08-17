package com.example.liangge.rxjavatest.custombody;

import android.util.Log;

import com.example.liangge.rxjavatest.intef.ProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by guhongliang on 2017/8/16.
 * 下载进度拦截器
 */

public class ProgressResponseBodyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(), new ProgressListener() {
                    @Override
                    public void update(long readLength, long contentLength, boolean done) {
                        Log.e(TAG, "当前下载量: " + readLength + "总大小: " + contentLength);
                    }
                }))
                .build();
    }
}
