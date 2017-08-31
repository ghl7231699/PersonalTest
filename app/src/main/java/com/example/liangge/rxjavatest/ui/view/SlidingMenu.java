package com.example.liangge.rxjavatest.ui.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.liangge.rxjavatest.ui.adapter.SlideAdapter;

/**
 * Created by guhongliang on 2017/8/30.
 * 实现侧滑删除效果
 */

public class SlidingMenu extends HorizontalScrollView {
    //菜单占屏幕的占比
    private static final float radio = 0.3f;
    //屏幕的宽度
    private int mWidth;
    //菜单的宽度
    private int mMenuWidth;
    private boolean once = true;
    //是否打开侧滑
    private boolean isOpen;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager;
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (context instanceof Activity) {
            windowManager = ((Activity) context).getWindowManager();
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            mWidth = metrics.widthPixels;
            mMenuWidth = (int) (mWidth * radio);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width = mWidth;
            wrapper.getChildAt(1).getLayoutParams().width = mMenuWidth;
            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                closeOpenMenu();
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                boolean b = Math.abs(scrollX) > mMenuWidth / 2;
                if (b) {
                    //滑动距离超过一半 才进行移动，否则不显示侧滑菜单
                    this.smoothScrollTo(mMenuWidth, 0);
                    openMenu();
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    //关闭抽屉
    public void closeMenu() {
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    //是否打开抽屉
    public boolean isOpen() {
        return isOpen;
    }

    //打开侧滑时，记录此view
    private void openMenu() {
        View view = this;
        while (true) {
            view = (View) view.getParent();
            if (view instanceof RecyclerView) {
                break;
            }
        }
        ((SlideAdapter) ((RecyclerView) view).getAdapter()).holdOpenMenu(this);
        isOpen = true;
    }

    //触摸此view时，关闭上次打开的侧滑
    private void closeOpenMenu() {
        if (!isOpen) {
            View view = this;
            while (true) {
                view = (View) view.getParent();
                if (view instanceof RecyclerView) {
                    break;
                }
            }
            ((SlideAdapter) ((RecyclerView) view).getAdapter()).closeOpenMenu();
        }
    }
}
