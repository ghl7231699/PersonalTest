package com.example.taopiaopiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 没有安装
 * 上下文  是系统注入的
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.main_activity_click_iv);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_click_iv:
                Toast.makeText(that, "点击啦", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(that, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }


}
