package com.example.liangge.rxjavatest.presenter.contract;

/**
 * Created by guhongliang on 2017/4/6.
 */

public interface BaseView<T> {
//    void setPresenter(T presenter);

    void showError(String s);

    void onRequestPermissonSuccess();//获取权限

    void onRequestPermissonError();//拒绝权限
}
