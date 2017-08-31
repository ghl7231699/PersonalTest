package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.manager.PluginManager;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

/**
 * Created by guhongliang on 2017/8/26.
 */

public class ResultActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.result_activity_layout;
    }

    @Override
    public void initView() {
        PluginManager.getInstance().setContext(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    public void load(View view) {
        PluginManager.getInstance().loadPath("");
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
//        getClassLoader().loadClass("");
//        Class.forName("")
//        必须安装才能获取
        intent.putExtra("key", PluginManager.getInstance().getEntryName());
        startActivity(intent);
    }
}
