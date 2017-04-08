package com.example.liangge.rxjavatest.ui.activity.baseactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private SparseArray<View> mViews;

    //获取布局Id
    public  abstract int getLayoutId();

    //初始化view
    public abstract void initView();

    //初始化监听事件
    public abstract void initListener();

    //初始化数据
    public  abstract void initData();

    //处理点击事件
    public abstract void processClick(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        initView();
        initListener();
        initData();
    }

    //通过Id找到view
    public <E extends View> E findView(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    //View设置OnClick事件
    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }
}
