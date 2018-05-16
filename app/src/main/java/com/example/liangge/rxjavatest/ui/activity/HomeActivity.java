package com.example.liangge.rxjavatest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.ndk.NdkActivity;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.PdfDownLoadActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getIntentValue();
        ButterKnife.bind(this);
    }

    private void getIntentValue() {
        Uri uri = getIntent().getData();
        //Uri 规则: scheme://host+path?query
        //这里以“https://yaoyi.ypzdw.com/article/413?type=url&from=web”为例
        String scheme = uri.getScheme(); //https , "://" 这个前面的就表示scheme
        String host = uri.getHost(); //yaoyi.ypzdw.com
        String path = uri.getPath(); //article/413
        String query = uri.getQuery(); //type=mobile&from=web
        String type = uri.getQueryParameter("type"); //url
        Log.d(HomeActivity.class.getSimpleName(), "scheme = " + scheme + ", host = " + host + ", path = " + path + ", query = " + query + ", type = " + type);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22})
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
            case R.id.btn14:
                toOtherActivity(InterviewActivity.class);
                break;
            case R.id.btn15:
                toOtherActivity(RetrofitActivity.class);
                break;
            case R.id.btn16:
                toOtherActivity(RecyclerViewActivity.class);
                break;
            case R.id.btn17:
                toOtherActivity(NiceDialogActivity.class);
                break;
            case R.id.btn18:
                toOtherActivity(SlideActivity.class);
                break;
            case R.id.btn19:
                toOtherActivity(TestActivity.class);
                break;
            case R.id.btn20:
                toOtherActivity(NdkActivity.class);
                break;
            case R.id.btn21:
                toOtherActivity(ReflectActivity.class);
                break;
            case R.id.btn22:
                toOtherActivity(TabActivity.class);
                break;
        }
    }

    private void toOtherActivity(Class<? extends Activity> clazz) {
        if (clazz != null) {
            Intent intent = new Intent(HomeActivity.this, clazz);
            startActivity(intent);
        } else {
            ToastUtils.toast("建设中");
        }
    }
}
