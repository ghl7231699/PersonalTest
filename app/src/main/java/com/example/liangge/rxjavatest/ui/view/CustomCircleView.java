package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.liangge.rxjavatest.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by guhongliang on 2017/6/13.
 */

public class CustomCircleView extends View {
    private String mContentText;//文本
    private int mContentColor;//文本的颜色
    private int mContentSize;//文本的大小
    private Paint mPaint;
    private Rect mRect;

    public CustomCircleView(Context context) {
        this(context, null);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取自定义样式属性
         */
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomCircleView, defStyleAttr, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.CustomCircleView_contentText:
                    mContentText = typedArray.getString(index);
                    break;
                case R.styleable.CustomCircleView_contentTextColor:
                    mContentColor = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomCircleView_contentTextSize:
                    //默认设置为16sp,typeValue也可以把sp转化成px
                    int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
                    mContentSize = typedArray.getDimensionPixelSize(index, v);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setTextSize(mContentSize);
        mRect = new Rect();
        mPaint.getTextBounds(mContentText, 0, mContentText.length(), mRect);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentText = randomText();
                postInvalidate();
            }
        });
    }

    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }

        return sb.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getHeight() / 2;
        int w = getWidth() / 2;
        mPaint.setColor(mContentColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(mContentText, w, getHeight() / 5, mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
//        参数的意义，分别代表圆心的x坐标，y坐标，半径，paint
        canvas.drawCircle(w, h, 200, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(w + 200, h, 200, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(w + 100, h - 200, 200, mPaint);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }
}
