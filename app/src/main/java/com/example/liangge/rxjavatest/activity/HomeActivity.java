package com.example.liangge.rxjavatest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
//    @BindView(R.id.btn1)
//    Button mBtn1;
//    @BindView(R.id.btn2)
//    Button mBtn2;
//    @BindView(R.id.btn3)
//    Button mBtn3;
//    @BindView(R.id.btn4)
//    Button mBtn4;
//    @BindView(R.id.btn5)
//    Button mBtn5;
//    @BindView(R.id.btn6)
//    Button mBtn6;
//    @BindView(R.id.btn7)
//    Button mBtn7;
//    @BindView(R.id.btn8)
//    Button mBtn8;
//    @BindView(R.id.btn9)
//    Button mBtn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                toOtherActivity(AuthCodeActivity.class);
                break;
            case R.id.btn2:
                toOtherActivity(DaggerActivity.class);
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
                toOtherActivity(null);
                break;
            case R.id.btn8:
                toOtherActivity(null);
                break;
            case R.id.btn9:
                toOtherActivity(null);
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
