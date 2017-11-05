package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ghl11 on 2017/11/5.
 */

public class ManiActivity extends AppCompatActivity {
    private TextView mTv;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
