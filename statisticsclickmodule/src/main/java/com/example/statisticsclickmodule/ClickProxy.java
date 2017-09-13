package com.example.statisticsclickmodule;

import android.view.View;

/**
 * Created by guhongliang on 2017/9/12.
 */

public class ClickProxy extends IOnClick<View, ClickListener> {
    private View mView;

    public ClickProxy(View view, ClickListener clickListener) {
        super(view, clickListener);
        mView = view;
    }

    public void show() {
        mV.setOnClick(mView);
    }
}
