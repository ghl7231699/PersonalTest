package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.liangge.rxjavatest.R;

/**
 * Created by guhongliang on 2017/9/6.
 */

public class TimeLineMakerView extends View {
    private int margin_left;//距离左边距离
    private int margin_right;
    private int margin_top;

    private int circle_size = 24;//圆的大小
    private int line_size = 2;//长线的宽度

    private int width;
    private int height;
    private Paint mPaint;


    private Drawable marker;
    private Drawable line;
    private boolean orientation;//true 代表的是水平

    public TimeLineMakerView(Context context) {
        this(context, null);
    }

    public TimeLineMakerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineMakerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimeLineMakerView);
        circle_size = typedArray.getDimensionPixelSize(R.styleable.TimeLineMakerView_circle_width, circle_size);
        line_size = typedArray.getDimensionPixelSize(R.styleable.TimeLineMakerView_line_width, line_size);

        marker = typedArray.getDrawable(R.styleable.TimeLineMakerView_marker);
        line = typedArray.getDrawable(R.styleable.TimeLineMakerView_line);

        orientation = typedArray.getBoolean(R.styleable.TimeLineMakerView_orientation, orientation);
        typedArray.recycle();
        if (marker == null) {
            marker.setCallback(this);
        }
        if (line == null) {
            line.setCallback(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (orientation) {
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, 80);
            } else if (widthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, height);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(width, 80);
            }
        }
        //垂直方向的
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        if (marker != null) {
            marker.draw(canvas);
        }
        if (line != null) {
            line.draw(canvas);
        }
    }
}
