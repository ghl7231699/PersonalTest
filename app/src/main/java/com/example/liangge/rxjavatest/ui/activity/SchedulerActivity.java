package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.liangge.rxjavatest.di.data.Api;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.User;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liangge on 2017/3/26.
 */

public class SchedulerActivity extends AppCompatActivity {
    private TextView tv;
    private Api api;
    private String url = "http//:192.168.1.189.5000/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduler_activity_layout);
        tv = (TextView) findViewById(R.id.schedule_text);
        try {
            RetrofitInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RetrofitInitialize() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public void onclick(View view) {
        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {
                User body = api.getUserInfoWithPath(1).execute().body();
                //请求network
                User user = new User("1", "王者榮耀", "邓志飞是个二货吧");
                e.onNext(user);
            }
        }).subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                Log.d("SchedulerActivity", "" + user);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
