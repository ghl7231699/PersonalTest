package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.MessageEvent;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guhongliang on 2017/7/31.
 */

public class EventBusActivity extends BaseActivity {
    @BindView(R.id.event_bus_btn)
    Button mBtn;
    @BindView(R.id.event_bus_strike_content)
    TextView mContent;
    private MessageEvent mEvent;

    @Override
    public int getLayoutId() {
        return R.layout.event_bus_activity_layout;
    }


    @Override
    public void initView() {
        EventBus.getDefault()
                .register(this);
    }


    @Override
    public void initData() {
        mEvent = new MessageEvent();
        mEvent.setChange(true);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toast("点击发送");
                EventBus.getDefault().post(mEvent);
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void ononMoonStickyEvent(MessageEvent messageEvent) {
        if (messageEvent.isChange()) {
            mContent.setText("获取到粘性事件");
        }
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick(R.id.event_bus_strike_btn)
    public void onViewClicked() {

    }
}
