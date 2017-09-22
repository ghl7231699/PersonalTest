package com.example.bargraph;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by guhongliang on 2017/9/19.
 */

public class ContentAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;

    private Context mContext;

    public ContentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;

    }

    @Override
    public Fragment getItem(int position) {

        return ContentFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
