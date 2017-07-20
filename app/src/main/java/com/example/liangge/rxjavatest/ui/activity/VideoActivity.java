package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import java.io.File;

import butterknife.BindView;

/**
 * Created by guhongliang on 2017/7/19.
 */

public class VideoActivity extends BaseActivity {
    @BindView(R.id.video_record_btn)
    Button mBtn;
    private String path = Environment.getExternalStorageDirectory() + "Video" + File.separator;

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
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
    }
}
