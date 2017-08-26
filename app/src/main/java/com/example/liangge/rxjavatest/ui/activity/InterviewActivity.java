package com.example.liangge.rxjavatest.ui.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.service.MessengerService;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import butterknife.OnClick;

/**
 * Created by guhongliang on 2017/8/14.
 */

public class InterviewActivity extends BaseActivity {
    private static final String TAG = "InterviewActivity";
    private ProgressDialog p;
    private Messenger mService;

    @Override
    public int getLayoutId() {
        return R.layout.interview_activity_layout;
    }

    @Override
    public void initView() {
        p = new ProgressDialog(this);
        p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        p.setCancelable(true);// 设置是否可以通过点击Back键取消
        p.setCanceledOnTouchOutside(false);
        p.setMessage("Loading");
        p.setMax(10);
        p.setTitle("Warn");

        //
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.sleep_btn, R.id.wait_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sleep_btn:
                p.show();
                new java.lang.Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 1;
                        while (i < 11) {
                            try {
                                java.lang.Thread.sleep(1000);
                                p.setProgress(i);
                                i++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.wait_btn:
                int a = 100485795;
                String s = String.valueOf(a);
                StringBuilder sb = new StringBuilder(s);
                sb.reverse();
                Log.e(TAG, "onViewClicked: " + sb);
                if (sb.length() < 8) {
                    ToastUtils.toast("Wrong input");
                    return;
                }
                String substring = sb.substring(3, 7);
                Log.e(TAG, "onViewClicked: " + substring);
                break;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message message = Message.obtain(null, 1);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello,this is client.");
            message.setData(bundle);
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
