package com.example.liangge.rxjavatest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.PdfDownLoadActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                toOtherActivity(AuthCodeActivity.class);
                break;
            case R.id.btn2:
                toOtherActivity(null);
                break;
            case R.id.btn3:
                toOtherActivity(MaterialDesignActivity.class);
                break;
            case R.id.btn4:
                toOtherActivity(MapActivity.class);
                break;
            case R.id.btn5:
                toOtherActivity(MainActivity.class);
                break;
            case R.id.btn6:
                toOtherActivity(UserInfoActivity.class);
                break;
            case R.id.btn7:
                toOtherActivity(PdfDownLoadActivity.class);
                break;
            case R.id.btn8:
                toOtherActivity(RxDownLoadActivity.class);
                break;
            case R.id.btn9:
                toOtherActivity(CustomActivity.class);
                break;
            case R.id.btn10:
                toOtherActivity(VideoActivity.class);
                break;
            case R.id.btn11:
                toOtherActivity(RxjavaActivity.class);
                break;
            case R.id.btn12:
                toOtherActivity(PaintViewActivity.class);
                break;
            case R.id.btn13:
                toOtherActivity(OkHttpActivity.class);
                break;
        }
    }

    private void toOtherActivity(Class<? extends Activity> clazz) {
        if (clazz != null) {
            Intent intent = new Intent(HomeActivity.this, clazz);
            startActivity(intent);
        } else {
            Toast.makeText(this, "建设中。。。。", Toast.LENGTH_SHORT).show();
        }
    }
}
