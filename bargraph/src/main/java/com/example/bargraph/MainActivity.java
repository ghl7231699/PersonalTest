package com.example.bargraph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bargraph.bean.HorizontalBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    BarGraphView mBgv;
    HorizontalBarGraphView mHorizontalBar;
    private Button mButton;
    private List<BarGraphView.Bar> mList = new ArrayList<>();
    private BarGraphView.Bar bar;
    private HorizontalBar mBar;
    private List<HorizontalBar> mBars = new ArrayList<>();
    private int max;//返回的item所有值的和的最大值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBgv = (BarGraphView) findViewById(R.id.bgv);
        mHorizontalBar = (HorizontalBarGraphView) findViewById(R.id.h_bar);
        mButton = (Button) findViewById(R.id.go);
        mHorizontalBar.setVisibility(View.VISIBLE);
        initBar();
    }

    private void initBar() {
        bar = new BarGraphView.Bar(30);
        mList.add(bar);
        bar = new BarGraphView.Bar(50);
        mList.add(bar);
        bar = new BarGraphView.Bar(70);
        mList.add(bar);
        bar = new BarGraphView.Bar(20);
        mList.add(bar);
        bar = new BarGraphView.Bar(80);
        mList.add(bar);
        bar = new BarGraphView.Bar(90);
        mList.add(bar);
        bar = new BarGraphView.Bar(60);
        mList.add(bar);
        bar = new BarGraphView.Bar(90);
        mList.add(bar);
        bar = new BarGraphView.Bar(60);
        mList.add(bar);
        bar = new BarGraphView.Bar(100);
        mList.add(bar);
        mBgv.setMarginLeft(100)
                .setMarginBottom(20)
                .setBars(mList);

        for (int i = 0; i < 10; i++) {
            mBar = new HorizontalBar();
            Random random = new Random();
            int nextInt1 = random.nextInt(20);
            int nextInt2 = random.nextInt(6);
            int nextInt3 = random.nextInt(6);
//            String[] names = {"李丽", "川普", "普京", "阿三"};
//            mBar.setUserName(names[random.nextInt(3)]);
            String[] names = {"李丽", "川普", "普京", "阿三"};
            mBar.setUserName(String.valueOf(i));
            mBar.setActivityNo(nextInt1);
            mBar.setIncreasedNo(nextInt2);
            mBar.setClaimNo(nextInt3);
            mBars.add(mBar);
        }

        for (HorizontalBar bar :
                mBars) {
            if (bar != null) {
                int total = bar.getActivityNo() + bar.getClaimNo() + bar.getIncreasedNo();
                if (total > max) {
                    max = total;
                }
            }
        }
//        mHorizontalBar.setChildTextColor(R.color.text_color);
        mHorizontalBar
                .setDefault_height(max)
                .setColors(new String[]{"#EE8F52", "#F4C65F", "#F8E37D"})
                .setTitle(new String[]{"新增量", "激活量", "认领量"})
                .setBars(mBars);
    }

}
