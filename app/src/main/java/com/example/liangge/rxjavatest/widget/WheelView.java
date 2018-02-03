package com.example.liangge.rxjavatest.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.liangge.rxjavatest.ui.view.UnreadMessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2018/1/9.
 * 水平滑动view  除第一项与最后一项外，其余选中项默认居中显示
 */

public class WheelView extends HorizontalScrollView {
    private List<View> mViews;
    private String name;
    private LinearLayout mLayout;

    private int width;
    private int height;

    public void setViews(List<View> views) {
        mViews = views;
        postInvalidate();
    }

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        UnreadMessageView messageView;
        mViews = new ArrayList<>();
        mLayout = new LinearLayout(context);
        for (int i = 0; i < 5; i++) {
            messageView = new UnreadMessageView(context);
            messageView.setContent("未读消息" + i);
            messageView.setNum(String.valueOf(i));
            messageView.setColor(Color.BLUE);
            mViews.add(messageView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        width = 500;
//        height = 50;
//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
