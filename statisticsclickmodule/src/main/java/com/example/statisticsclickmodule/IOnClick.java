package com.example.statisticsclickmodule;

/**
 * Created by guhongliang on 2017/9/12.
 */

public class IOnClick<M, V extends ClickListener> {
    protected M mM;
    protected V mV;

    public IOnClick(M m, V v) {
        mM = m;
        mV = v;
    }
}
