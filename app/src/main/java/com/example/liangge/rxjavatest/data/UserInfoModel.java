package com.example.liangge.rxjavatest.data;

import android.util.Log;

import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.utils.BeanTest;
import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guhongliang on 2017/4/6.
 */

public class UserInfoModel {
    private Api mApi;

    public UserInfoModel(Api api) {
        mApi = api;
    }

    public Observable<BeanTest> LoadUserInfo(UserParam param) {
        return mApi.UserLogin(param);
//        Observable.just(param)
//                .flatMap(new Function<UserParam, ObservableSource<BeanTest>>() {
//                    @Override
//                    public ObservableSource<BeanTest> apply(@NonNull UserParam userParam) throws Exception {
//                        BeanTest login = mApi.login(userParam).execute().body();
//                        Observable<BeanTest> beanTestObservable = mApi.UserLogin(userParam);
//                        return Observable.just(login);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<BeanTest>() {
//                    @Override
//                    public void accept(@NonNull BeanTest beanTest) throws Exception {
//                        if (beanTest != null) {
//                            String s = new Gson().toJson(beanTest);
//                            Log.d("MapActivity", "username= " + s);
//                            view.showUserInfo(beanTest);
//                            view.disMissLoading();
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        throwable.printStackTrace();
//                        view.disMissLoading();
//                        view.showError(throwable.getMessage());
//                    }
//                });
    }
}
