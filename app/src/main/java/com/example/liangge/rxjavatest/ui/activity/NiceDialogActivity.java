package com.example.liangge.rxjavatest.ui.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.view.DashedLine;
import com.example.mylibrary.BaseNiceDialog;
import com.example.mylibrary.NiceDialog;
import com.example.mylibrary.ViewConvertListener;
import com.example.mylibrary.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by guhongliang on 2017/9/5.
 */

public class NiceDialogActivity extends BaseActivity {
    @BindView(R.id.dl)
    DashedLine mDl;
    private Animation mAnimation;

    @Override
    public int getLayoutId() {
        return R.layout.nice_dialog_activity_layout;
    }

    @Override
    public void initView() {
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.ad_enter_anim);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.button1:
                NiceDialog.init()
                        .setLayoutId(R.layout.share_layout)
                        .setShowBottom(true)
                        .setDimAmount(0.3f)
                        .show(getSupportFragmentManager());
                break;
            case R.id.button2:
                NiceDialog.init().setLayoutId(R.layout.friend_set_layout)
                        .setDimAmount(0.3f)
                        .setShowBottom(true)
                        .setOutCancel(false)
                        .show(getSupportFragmentManager());
                break;
            case R.id.button3:
                mDl.startAnimation(mAnimation);
                NiceDialog.init()
                        .setLayoutId(R.layout.commit_layout)
                        .setOutCancel(false)
                        .setShowBottom(true)
                        .show(getSupportFragmentManager());
                break;
            case R.id.button4:
                NiceDialog.init()
                        .setLayoutId(R.layout.ad_layout)
                        .setWidth(300).setOutCancel(false)
                        .setAnimStyle(R.style.RedPacketAnim)
                        .show(getSupportFragmentManager());
                break;
            case R.id.button5:
                NiceDialog.init()
                        .setLayoutId(R.layout.loading_layout)
                        .setListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder viewHolder, BaseNiceDialog dialog) {
                                viewHolder.setOnClickListener(R.id.load_content, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ToastUtils.toast("点击了loading");
                                    }
                                });
                            }
                        }).show(getSupportFragmentManager());
                break;
            case R.id.button6:
                NiceDialog.init()
                        .setLayoutId(R.layout.confirm_layout)
                        .setListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder viewHolder, final BaseNiceDialog dialog) {
                                viewHolder.setText(R.id.title, "提示");
                                viewHolder.setText(R.id.message, "你确定要支付吗？");
                                viewHolder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                viewHolder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ToastUtils.toast("点击了确定");
                                        dialog.dismiss();

                                    }
                                });
                            }
                        })
                        .setShowBottom(false)
                        .setDimAmount(0.3f)
                        .setMargin(20)
                        .setOutCancel(false)
                        .show(getSupportFragmentManager());
                break;
        }
    }
}
