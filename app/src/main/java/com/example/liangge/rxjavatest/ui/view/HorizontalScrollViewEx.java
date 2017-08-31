package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by guhongliang on 2017/8/27.
 */

public class HorizontalScrollViewEx extends ViewGroup {
    private static  final  String TAG="HorizontalScrollViewEx";
    public HorizontalScrollViewEx(Context context) {
        super(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
