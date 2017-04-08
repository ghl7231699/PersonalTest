package com.example.liangge.rxjavatest.presenter;

import android.util.Log;
import android.util.Pair;

import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.common.constant.Data;
import com.example.liangge.rxjavatest.common.constant.Header;
import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.BeanTest;
import com.example.liangge.rxjavatest.common.utils.RetrofitUtil;
import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.di.component.DaggerAppComponent;
import com.example.liangge.rxjavatest.di.modules.HttpModule;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.google.gson.Gson;

import javax.inject.Inject;

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

public class LoginPresenter implements UserInfoContract.Presenter {
    private Api mApi;
    private UserInfoContract.View mView;

    public LoginPresenter(UserInfoContract.View view, Api api) {
        mView = view;
        this.mApi = api;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        LoadUserInfo();
    }

    @Override
    public void LoadUserInfo() {
        mView.showLoading();
//        mApi = RetrofitUtil.retrofitInitialize(HttpUrls.URL_LOGIN_IP);
        Observable.just(getUserParam())
                .flatMap(new Function<UserParam, ObservableSource<BeanTest>>() {
                    @Override
                    public ObservableSource<BeanTest> apply(@NonNull UserParam userParam) throws Exception {
                        BeanTest login = mApi.login(userParam).execute().body();
                        return Observable.just(login);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BeanTest>() {
                    @Override
                    public void accept(@NonNull BeanTest beanTest) throws Exception {
                        if (beanTest != null) {
                            String s = new Gson().toJson(beanTest);
                            Log.d("MapActivity", "username= " + s);
                            mView.showUserInfo(beanTest);
                            mView.disMissLoading();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mView.disMissLoading();
                        mView.showError(throwable.getMessage());
                    }
                });
    }

    private UserParam getUserParam() {
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "ebt-003");
        Data data = new Data("19680106", "17205261");
        UserParam param = new UserParam();
        param.setHeader(header);
        param.setUserData(data);
        return param;
    }
}
