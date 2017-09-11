package com.example.bargraph;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2017/9/8.
 */

public class SecondActivity extends AppCompatActivity {
    private BarGraphView bv1, bv2, bv3;
    private List<BarGraphView.Bar> mList, mList2, mList3;
    private BarGraphView.Bar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_layout);
        bv1 = (BarGraphView) findViewById(R.id.bv1);
        bv2 = (BarGraphView) findViewById(R.id.bv2);
        bv3 = (BarGraphView) findViewById(R.id.bv3);

        mList = new ArrayList<>();
        bar = new BarGraphView.Bar(50);
        mList.add(bar);
        bv1.setMarginLeft(100)
                .setMarginBottom(20)
                .setBars(mList);


        mList2 = new ArrayList<>();
        bar = new BarGraphView.Bar(70);
        mList2.add(bar);
        bar = new BarGraphView.Bar(40);
        mList2.add(bar);
        bv2.setMarginLeft(100)
                .setMarginBottom(20)
                .setBars(mList2);

        mList3 = new ArrayList<>();
        bar = new BarGraphView.Bar(70);
        mList3.add(bar);
        bar = new BarGraphView.Bar(40);
        mList3.add(bar);
        bar = new BarGraphView.Bar(50);
        mList3.add(bar);
        bv3.setMarginLeft(100)
                .setMarginBottom(20)
                .setBars(mList3);
    }
}
