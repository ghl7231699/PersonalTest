package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.fragment.DiscoveryFragment;
import com.example.liangge.rxjavatest.ui.fragment.MineFragment;
import com.example.liangge.rxjavatest.ui.fragment.TabFragment;
import com.example.liangge.rxjavatest.ui.fragment.VideoFragment;
import com.example.navigationbarlibrary.TabBarItem;
import com.example.navigationbarlibrary.TabBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2018/3/5.
 */

public class TabActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private TabBarLayout mTabBarLayout;
    private ImageView add;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mTabBarLayout = (TabBarLayout) findViewById(R.id.bbl);
        add = (ImageView) findViewById(R.id.ac_tab_add);
        add.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mViewPager.setOffscreenPageLimit(3);//最大预加载页面数
        TabFragment homeFragment = new TabFragment();
        mFragmentList.add(homeFragment);

        VideoFragment videoFragment = new VideoFragment();
        mFragmentList.add(videoFragment);

        DiscoveryFragment microFragment = new DiscoveryFragment();
        mFragmentList.add(microFragment);

        MineFragment meFragment = new MineFragment();
        mFragmentList.add(meFragment);

        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mTabBarLayout.setViewPager(mViewPager);
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ac_tab_add:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }


    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }
}
