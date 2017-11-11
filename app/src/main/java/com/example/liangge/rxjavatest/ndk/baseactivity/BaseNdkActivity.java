package com.example.liangge.rxjavatest.ndk.baseactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.liangge.rxjavatest.App;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类名称：
 * 类描述：ndk包下的所有activity的基类
 * 创建人：ghl
 * 创建时间：2017/11/10 下午2:33
 * 修改人：ghl
 * 修改时间：2017/11/10 下午2:33
 *
 * @version v1.0
 */

public abstract class BaseNdkActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    private App mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        this.mApp = (App) getApplication();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    //获取布局Id
    public abstract int getLayoutId();

    //初始化view
    public abstract void initView();

    //初始化数据
    public abstract void initData();
}
