package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.view.PercentView;

import java.io.File;

import butterknife.BindView;

/**
 * Created by guhongliang on 2017/7/19.
 */

public class VideoActivity extends BaseActivity {
    @BindView(R.id.video_record_btn)
    Button mBtn;
    @BindView(R.id.video_record_pv)
    PercentView mPv;
    private String path = Environment.getExternalStorageDirectory() + "Video" + File.separator;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int i = (int) msg.obj;
//                    mPv.getProgress(i);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.vedio_record_activity_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri uri = Uri.fromFile(new File(path));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 100);
                startActivityForResult(intent, 999);
            }
        });
        getPercent();
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    private void getPercent() {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 101; i++) {
                        sleep(30);
                        mPv.getProgress(i, 100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
