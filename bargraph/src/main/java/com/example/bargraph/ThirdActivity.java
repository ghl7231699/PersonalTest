package com.example.bargraph;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class ThirdActivity extends AppCompatActivity {
    private TableLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initView();

        mViewPager.setAdapter(new ContentAdapter(this, getSupportFragmentManager()));

    }

    private void initView() {
        mTabLayout = (TableLayout) findViewById(R.id.timeline_tl);
        mViewPager = (ViewPager) findViewById(R.id.timeline_vp);
    }

}
