package com.example.statisticsclickmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by guhongliang on 2017/9/12.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, ClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        this.setOnClick(view);
    }

    @Override
    public void setOnClick(View view) {
        if (view != null) {
            Log.e(ClassUtils.TAG, "setOnClick: " + view.getId() + "\n" + view.getClass().getName());
        }
    }

    @Override
    public void setCustomClick(int clickId, String clickName) {
        Log.e(ClassUtils.TAG, "setCustomClick: " + clickId + "\n" + clickName);
    }
}
