package com.example.bargraph;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    BarGraphView mBgv;
    private Button mButton;

    private List<BarGraphView.Bar> mList = new ArrayList<>();
    private BarGraphView.Bar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBgv = (BarGraphView) findViewById(R.id.bgv);
        mButton = (Button) findViewById(R.id.btn);
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
    }
}
