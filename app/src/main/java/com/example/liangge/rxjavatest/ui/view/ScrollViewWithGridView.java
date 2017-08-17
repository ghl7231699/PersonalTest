package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by guhongliang on 2017/8/16.
 * scrollview 中 嵌套GridView 冲突
 */

public class ScrollViewWithGridView extends GridView {
    private boolean haveScrollbar = true;

    public ScrollViewWithGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollViewWithGridView(Context context) {
        super(context);
    }

    public ScrollViewWithGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否有ScrollBar，当要在ScrollView中显示时，应当设置为false。 默认为 true
     *
     * @param haveScrollbar
     */
    public void setHaveScrollbar(boolean haveScrollbar) {
        this.haveScrollbar = haveScrollbar;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (haveScrollbar == false) {
            MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
