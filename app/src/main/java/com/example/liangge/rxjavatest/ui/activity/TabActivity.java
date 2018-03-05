package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.fragment.HeadLineFragment;
import com.example.liangge.rxjavatest.ui.fragment.TabFragment;
import com.example.liangge.rxjavatest.ui.fragment.VideoFragment;
import com.example.navigationbarlibrary.TabBarItem;
import com.example.navigationbarlibrary.TabBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2018/3/5.
 */

public class TabActivity extends BaseActivity {
    private ViewPager mViewPager;
    private TabBarLayout mTabBarLayout;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mTabBarLayout = (TabBarLayout) findViewById(R.id.bbl);
    }

    @Override
    public void initData() {
        TabFragment homeFragment = new TabFragment();
        mFragmentList.add(homeFragment);

        VideoFragment videoFragment = new VideoFragment();
        mFragmentList.add(videoFragment);

        HeadLineFragment microFragment = new HeadLineFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString(TabFragment.CONTENT, "微头条");
        microFragment.setArguments(bundle3);
        mFragmentList.add(microFragment);

        TabFragment meFragment = new TabFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString(TabFragment.CONTENT, "我的");
        meFragment.setArguments(bundle4);
        mFragmentList.add(meFragment);

        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mTabBarLayout.setViewPager(mViewPager);
        mTabBarLayout.setOnItemSelectedListener(new TabBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(TabBarItem tabBarItem, int previousPosition, int currentPosition) {

            }
        });
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

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
    }
}
