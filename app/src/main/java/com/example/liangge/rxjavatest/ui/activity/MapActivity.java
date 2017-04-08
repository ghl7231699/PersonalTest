package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Data;
import com.example.liangge.rxjavatest.common.constant.Header;
import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.utils.BeanTest;
import com.example.liangge.rxjavatest.common.utils.RetrofitUtil;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MapActivity extends AppCompatActivity {
    private String url = "https://123.127.246.11/";
    //    private String url = "http://10.1.92.243/";
    private Api api;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        tv = (TextView) findViewById(R.id.map_tv);
        RetrofitInitialize();
    }

    private void RetrofitInitialize() {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(SslContextFactory.getOkHttpClient())
//                .build();
//        api = retrofit.create(Api.class);
        api = RetrofitUtil.retrofitInitialize(url);
    }

    private UserParam getUserParam() {
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "ebt-003");
        Data data = new Data("19680106", "17205261");
//        Data data = new Data("123456", "01203668");
        UserParam param = new UserParam();
        param.setHeader(header);
        param.setUserData(data);
        return param;
    }

    public void onclick(View view) {
//        RetrofitHttpRequest();
        final Gson gson = new Gson();
        Observable.just(getUserParam())
                .flatMap(new Function<UserParam, ObservableSource<BeanTest>>() {
            @Override
            public ObservableSource<BeanTest> apply(UserParam param) throws Exception {
                BeanTest body = api.login(param).execute().body();
                Log.d("", "apply: " + body);
                return Observable.just(body);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BeanTest>() {
            @Override
            public void accept(BeanTest BeanTest) throws Exception {
                if (BeanTest != null) {
                    String s = gson.toJson(BeanTest);
                    com.example.liangge.rxjavatest.common.utils.BeanTest.DataBean user = BeanTest.getData();
                    Log.d("MapActivity", "username= " + s);
                    tv.setText(s);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                Toast.makeText(MapActivity.this, "请求出错了，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });

//        Observable<Integer> observable = Observable.just(1);
//        observable.map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return String.valueOf(integer);
//            }
//        });
    }

//    private void RetrofitHttpRequest() {
//        UserParam param = getUserParam();
//        Gson gson = new Gson();
//        String s = gson.toJson(param, UserParam.class);
//        Log.d("", "onclick: " + s);
//        api.login(param).enqueue(new Callback<BaseResult>() {
//            @Override
//            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
//                Log.d("MapActivity", "onResponse: ");
//                tv.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<BaseResult> call, Throwable t) {
//                Log.d("MapActivity", "onFailure: " + call.toString());
//                t.printStackTrace();
//            }
//        });
//    }
}
