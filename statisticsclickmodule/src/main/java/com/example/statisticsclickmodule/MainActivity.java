package com.example.statisticsclickmodule;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements ClickListener {
    private Button mButton, mBtn;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn_click);
        mBtn = (Button) findViewById(R.id.btn2);
        mButton.setOnClickListener(this);
        mBtn.setOnClickListener(this);
        String activity = ClassUtils.getCurrentActivity(this);
        sb.append(activity);
        String deviceId = ClassUtils.getDeviceId(getApplicationContext());
        sb.append(deviceId);
        ClickAgent.init(this);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.super.setOnClick(view);
                Toast.makeText(MainActivity.this, "点击了第二个", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                Toast.makeText(MainActivity.this, "点击了第一个", Toast.LENGTH_SHORT).show();
                super.setCustomClick(1, "搜索房源按钮");
                break;
            default:
                break;
        }
    }
}
