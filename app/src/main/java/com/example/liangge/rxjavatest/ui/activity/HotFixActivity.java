package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Calculator;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guhongliang on 2017/8/12.
 */

public class HotFixActivity extends BaseActivity {

    @BindView(R.id.hot_fix_result)
    Button mHotFixResult;

    @Override
    public int getLayoutId() {
        return R.layout.hot_fix_activity_layout;
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

    @OnClick({R.id.hot_fix_calculator, R.id.hot_fix})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hot_fix_calculator:
                Calculator calculator = new Calculator();
                int calculator1 = calculator.calculator();
                mHotFixResult.setText("" + calculator);
                break;
            case R.id.hot_fix:
                break;
        }
    }
}
