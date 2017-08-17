package com.example.liangge.rxjavatest.custombody;

import android.util.Log;

import com.example.liangge.rxjavatest.bean.MessageEvent;
import com.example.liangge.rxjavatest.intef.ProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by guhongliang on 2017/8/17.
 * 获取下载进度辅助help
 */

public class ProgressHelper {
    private static MessageEvent messageEvent;
    private static ProgressHandler mH;

    public static OkHttpClient.Builder addProgress(OkHttpClient.Builder builder) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }
        final ProgressListener progressListener = new ProgressListener() {
            @Override
            public void update(long readLength, long contentLength, boolean done) {
                Log.d("progress:", String.format("%d%% done\n", (100 * readLength) / contentLength));
                messageEvent = new MessageEvent(contentLength, readLength);
                messageEvent.setChange(done);
                mH.sendMessage(messageEvent);
            }
        };
        //添加拦截器，自定义ResponseBody，添加下载进度
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response
                        .newBuilder()
                        .body(new ProgressResponseBody(response.body(), progressListener))
                        .build();
            }
        });
        return builder;
    }

    public void setProgressHandler(ProgressHandler handler) {
        mH = handler;
    }
}
