package com.example.liangge.rxjavatest.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.widget.SeekBar;

import com.example.liangge.rxjavatest.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CardViewActivity extends AppCompatActivity {
    private CardView mCardView;
    private SeekBar mSeekBar1, mSeekBar2, mSeekBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        initView();
        initListener();
    }

    private void initWindow() {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        /**
         * 2、设置ShareElement外其他view的退出方式（左边划出）
         */
        getWindow().setExitTransition(new Slide(Gravity.START));


        Slide slide = new Slide(Gravity.END);//右侧平移
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.START);

        Explode explode = new Explode();//展开回收
        getWindow().setEnterTransition(explode);
        getWindow().setReturnTransition(slide);
    }

    private void initView() {
        mCardView = (CardView) findViewById(R.id.card_item);
        mSeekBar1 = (SeekBar) findViewById(R.id.sb1);
        mSeekBar2 = (SeekBar) findViewById(R.id.sb2);
        mSeekBar3 = (SeekBar) findViewById(R.id.sb3);
    }

    private void initListener() {
        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setCardElevation(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setContentPadding(progress, progress, progress, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}

