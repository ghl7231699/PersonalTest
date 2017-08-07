package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.MessageEvent;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.view.CustomControlView;
import com.example.liangge.rxjavatest.ui.view.PercentView;
import com.example.liangge.rxjavatest.ui.view.RoundImage;
import com.example.liangge.rxjavatest.ui.view.RoundImageViewByXfermode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guhongliang on 2017/7/19.
 */

public class VideoActivity extends BaseActivity {
    @BindView(R.id.video_record_btn)
    Button mBtn;
    @BindView(R.id.video_record_pv)
    PercentView mPv;
    @BindView(R.id.custom_activity_round_image)
    RoundImage mRoundImage;
    @BindView(R.id.custom_activity_round_by)
    RoundImageViewByXfermode mByXfermode;
    @BindView(R.id.start_next)
    Button mStartNext;
    @BindView(R.id.start_next_content)
    TextView mContent;
    @BindView(R.id.strike_next)
    TextView mTextView;
    @BindView(R.id.video_custom_rl1)
    CustomControlView mControlView1;
    @BindView(R.id.video_custom_rl2)
    CustomControlView mControlView2;
    private String path = Environment.getExternalStorageDirectory() + "Video" + File.separator;

    @Override
    public int getLayoutId() {
        return R.layout.vedio_record_activity_layout;
    }

    @Override
    public void initView() {
        EventBus.getDefault()
                .register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        mRoundImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.girl));
        mByXfermode.setType(0);
        mByXfermode.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.girl));
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
        mStartNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, EventBusActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(MessageEvent messageEvent) {
        if (messageEvent != null && messageEvent.isChange()) {
            mByXfermode.setVisibility(View.VISIBLE);
            mContent.setText("copy that");
        }

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


    @OnClick({R.id.strike_next, R.id.strike_subscribe, R.id.video_custom_rl1, R.id.video_custom_rl2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.strike_next:
                break;
            case R.id.strike_subscribe:
                EventBus.getDefault().postSticky(new MessageEvent(true));
                break;
            case R.id.video_custom_rl1:
                mControlView1.getTitleBarRightBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.toast("点击了右边按钮");
                    }
                });
                break;
            case R.id.video_custom_rl2:
                mControlView1.getTitleBarLeftBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.toast("点击了左边按钮");
                    }
                });
                break;
        }
    }
}
