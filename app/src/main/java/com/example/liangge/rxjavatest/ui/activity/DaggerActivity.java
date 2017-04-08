package com.example.liangge.rxjavatest.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.common.constant.UserManager;

import javax.inject.Inject;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class DaggerActivity extends BaseActivity {
    private TextView tv;
    private Button btn;
//    @Inject
//    ApiService mApiService;
    @Inject
    UserManager  mUserManager;
    @Override
    public int getLayoutId() {
        return R.layout.activity_dagger;
    }

    @Override
    public void initView() {
        //初始化view
        tv = findView(R.id.dagger_text);
        btn = findView(R.id.dagger_button);
    }

    @Override
    public void initListener() {
        //初始化点击事件
        setOnClick(tv);
        setOnClick(btn);

    }

    @Override
    public void initData() {
//        DaggerUserComponent.create().inject(this);
//        mApiService.register();
//        DaggerUserComponent.create().inject(this);
        mUserManager.register();
    }

    @Override
    public void processClick(View v) {
        //处理点击事件
        switch (v.getId()) {
            case R.id.dagger_text:
                break;
            case R.id.dagger_button:
                break;
        }
    }
}
