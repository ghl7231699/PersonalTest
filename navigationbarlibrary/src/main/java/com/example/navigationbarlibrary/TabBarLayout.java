package com.example.navigationbarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：TabBarLayout
 * 类描述：底部导航栏container
 * 创建人：ghl
 * 创建时间：2018/3/5 上午10:54
 * 修改人：ghl
 * 修改时间：2018/3/5 上午10:54
 *
 * @version v1.0
 */

public class TabBarLayout extends LinearLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private List<TabBarItem> mViews = new ArrayList<>();//存放item的集合
    private int itemCount;//childItem数目
    private int currentItem;//当前引用的item
    private ViewPager mViewPager;
    private boolean mSmoothScroll;

    public TabBarLayout(Context context) {
        this(context, null);
    }

    public TabBarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarLayout);
        mSmoothScroll = ta.getBoolean(R.styleable.BottomBarLayout_smoothScroll, false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    /**
     * 描述 初始化操作
     *
     * @Params:
     * @Return:
     */
    private void init() {
        itemCount = getChildCount();//获取子item的数目
        if (mViewPager != null) {
            if (mViewPager.getAdapter().getCount() != itemCount) {
                throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
            }
        }
        for (int i = 0; i < itemCount; i++) {
            if (mViews != null && getChildAt(i) instanceof TabBarItem) {
                TabBarItem childAt = (TabBarItem) getChildAt(i);
                mViews.add(childAt);
                try {
                    childAt.setOnClickListener(new TabBarListener(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException("TabBarLayout的子View必须是TabBarItem!");
            }
        }
        //设置当前选中项
        mViews.get(currentItem).setStatus(true);
        for (int i = 0; i < 4; i++) {
            mViews.get(i).setUnreadNum(i);
        }

        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(this);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        setSelectedTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class TabBarListener implements OnClickListener {
        private int position;

        public TabBarListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            //回调点击的位置
            if (mViewPager != null) {
                //有设置viewPager
                if (position == currentItem) {
                    //如果还是同个页签，使用setCurrentItem不会回调OnPageSelecte(),所以在此处需要回调点击监听
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(getTabBarItem(position), currentItem, position);
                    }
                } else {
                    mViewPager.setCurrentItem(position, mSmoothScroll);
                }
            } else {
                //没有设置viewPager
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(getTabBarItem(position), currentItem, position);
                }
            }
            currentItem = position;
            setSelectedTab(currentItem);
        }
    }

    private void setSelectedTab(int position) {
        if (mViews != null) {
            TabBarItem tabBarItem = mViews.get(position);
            for (TabBarItem it :
                    mViews) {
                if (tabBarItem == it) {
                    it.setStatus(true);
                } else {
                    it.setStatus(false);
                }
            }
        }
    }

    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(TabBarItem tabBarItem, int previousPosition, int currentPosition);
    }

    public TabBarItem getTabBarItem(int position) {
        return mViews.get(position);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        init();
    }
}
