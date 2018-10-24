package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

public class MultiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth_code;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }
}
