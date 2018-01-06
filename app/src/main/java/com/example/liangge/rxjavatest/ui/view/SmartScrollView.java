package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.liangge.rxjavatest.common.utils.ToastUtils;

import java.util.List;


/**
 * Created by guhongliang on 2018/1/5.
 */

public class SmartScrollView extends HorizontalScrollView {
    private LinearLayout mLayout;
    private List<String> datas;
    private UnreadMessageView mView;
    private Context mContext;

    public void setDatas(List<String> datas) {
        this.datas = datas;
        setHorizontalScrollBarEnabled(false);
        mLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayout.setLayoutParams(params);
        mLayout.setGravity(Gravity.CENTER);
        if (datas != null && !datas.isEmpty()) {
            for (int i = 0; i < datas.size(); i++) {
                mView = new UnreadMessageView(mContext);
                if (i == 0) {
                    mView.setColor(Color.BLUE);
                } else {
                    mView.setColor(Color.BLACK);
                }
                mView.setContent(datas.get(i));
                mView.setNum(String.valueOf(i));
                mView.setLayoutParams(p);
                mLayout.addView(mView);
                final int finalI = i;
                mView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean selected = mView.isSelected();
                        if (selected) {
                            return;
                        }
                        ToastUtils.toast("点击了第" + finalI + "个");
                        mView.setColor(Color.BLUE);
                    }
                });

            }
        }
        addView(mLayout);
    }

    public SmartScrollView(Context context) {
        this(context, null);
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = 1000;
//        int height = 150;
//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
