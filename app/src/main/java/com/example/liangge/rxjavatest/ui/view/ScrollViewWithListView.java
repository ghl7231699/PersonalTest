package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 类名称：ScrollViewWithListView
 * 类描述：scrollview 中 嵌套listview 简单实现
 * 创建人：ghl
 * 创建时间：2017/5/22 15:08
 * 修改人：ghl
 * 修改时间：2017/5/22 15:08
 *
 * @version v1.0
 */

public class ScrollViewWithListView extends ListView {
    public ScrollViewWithListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }
}
