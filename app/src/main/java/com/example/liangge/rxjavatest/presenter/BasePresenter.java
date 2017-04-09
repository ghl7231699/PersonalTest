package com.example.liangge.rxjavatest.presenter;

import com.example.liangge.rxjavatest.presenter.contract.BaseView;

/**
 * Created by guhongliang on 2017/4/6.
 */

public class BasePresenter<M,V extends BaseView> {
//    void start();
    protected  M mM;
    protected  V mV;

    public BasePresenter(M m, V v) {
        mM = m;
        mV = v;
    }
}
