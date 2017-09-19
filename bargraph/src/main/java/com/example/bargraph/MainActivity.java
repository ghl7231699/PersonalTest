package com.example.bargraph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    BarGraphView mBgv;
    HorizontalBarGraphView mHorizontalBar;
    private Button mButton;
    private List<BarGraphView.Bar> mList = new ArrayList<>();
    private BarGraphView.Bar bar;
    private HorizontalBarGraphView.HorizontalBar mBar;
    private List<HorizontalBarGraphView.HorizontalBar> mBars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBgv = (BarGraphView) findViewById(R.id.bgv);
        mHorizontalBar = (HorizontalBarGraphView) findViewById(R.id.h_bar);
        mButton = (Button) findViewById(R.id.go);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Panorama.class);
//                startActivity(intent);
                mHorizontalBar.setVisibility(View.VISIBLE);
            }
        });
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
        //
        mBar = new HorizontalBarGraphView.HorizontalBar(51, Color.argb(252, 252, 71, 88), "15000", "金汉王通讯研发中心");
        mBars.add(mBar);
        mBar = new HorizontalBarGraphView.HorizontalBar(80, Color.argb(252, 39, 210, 180), "12000", "领智中心");
        mBars.add(mBar);
        mBar = new HorizontalBarGraphView.HorizontalBar(80, Color.argb(252, 100, 179, 251), "12000", "领智中心");
        mBars.add(mBar);
//        mBar = new HorizontalBarGraphView.HorizontalBar(75, Color.argb(252, 100, 179, 251), "10000", "？？？");
//        mBars.add(mBar);
//        mBar = new HorizontalBarGraphView.HorizontalBar(55, Color.argb(252, 100, 179, 251), "10000", "领智中心");
//        mBars.add(mBar);
//        mBar = new HorizontalBarGraphView.HorizontalBar(55, Color.argb(252, 100, 179, 251), "10000", "领智中心");
//        mBars.add(mBar);
//        mHorizontalBar.setChildWidth(10);
        mHorizontalBar.setChildTextColor(R.color.text_color);
        mHorizontalBar.setBars(mBars);
    }

}
