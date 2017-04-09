package com.example.liangge.rxjavatest.di.modules;

import com.example.liangge.rxjavatest.common.config.SslContextFactory;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.data.http.Api;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liangge on 2017/4/8.
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //开发记录整个body，否则只记录基本信息，如返回200，http协议版本等
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(logging)
                //连接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(SslContextFactory.createSSLSocketFactory(), new SslContextFactory.TrustAllManager())
                .hostnameVerifier(new SslContextFactory.TrustAllHostnameVerifier())
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(HttpUrls.URL_LOGIN_IP)
                .build();
    }

    @Provides
    @Singleton
    public Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
