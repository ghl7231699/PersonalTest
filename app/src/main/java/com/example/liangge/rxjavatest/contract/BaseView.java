package com.example.liangge.rxjavatest.contract;

/**
 * Created by guhongliang on 2017/4/6.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showError(String s);
}
