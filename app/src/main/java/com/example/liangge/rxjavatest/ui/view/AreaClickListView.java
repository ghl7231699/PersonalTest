package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by guhongliang on 2017/10/18.
 * 区域点击listView
 */

public class AreaClickListView extends ListView {
    public AreaClickListView(Context context) {
        super(context);
    }

    public AreaClickListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AreaClickListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //获取子布局
//        View childAt = getChildAt(0);
//        LinearLayout childAt1 = (LinearLayout) childAt.getChildAt(1);
//        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_DOWN) {
//        boolean b = inRangeOfView(childAt, ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //获取子布局
        View childAt = getChildAt(0);
        LinearLayout childAt1 = null;
        if (childAt instanceof LinearLayout) {
            childAt1 = (LinearLayout) ((LinearLayout) childAt).getChildAt(0);
        }
        childAt1.getChildAt(1);
        boolean b = inRangeOfView(childAt1, ev);
        return b;
//        return super.dispatchTouchEvent(ev);
    }

    private boolean inRangeOfView(View view, MotionEvent ev) {
        int x = view.getWidth();
        int y = view.getHeight();
        float evX = ev.getX();
        float evY = ev.getY();
        if (evX < x / 2 || evY < y / 2) {
            return true;
        }
        return false;
    }
}
