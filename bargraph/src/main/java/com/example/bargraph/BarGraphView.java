package com.example.bargraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by guhongliang on 2017/9/6.
 */

public class BarGraphView extends View {
    private Paint mPaint;
    private int childWidth = 60;//条形图的宽度
    private int marginLeft;//距离左边界的距离
    private int marginBottom;
    private int space = Utils.dp2px(getContext(), 10);//相邻两个条形图之间的距离
    private int num = 0;//条形图的个数
    private int mWidth;//View的宽度
    private int mHeight;//View的高度
    private int default_height = 100;
    private int height;//条形图的高度
    private Rect mRf;
    private int[] color = {Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK, Color.GREEN, Color.GRAY};
    private String[] ySteps = new String[]{"0", "20k", "40k", "60k", "80k", "100k"};

    private List<Bar> mBars = new ArrayList<>();


    public BarGraphView(Context context) {
        this(context, null);
    }

    public BarGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParam();
    }

    /**
     * 初始化参数
     */
    private void initParam() {
        mRf = new Rect();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int screenWidth = Utils.getScreenWidth(getContext());
        int screenHeight = Utils.getScreenHeight(getContext());
        if (mode == MeasureSpec.EXACTLY) {
            mHeight = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(screenWidth, size);
        }
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (modeWidth == MeasureSpec.EXACTLY) {
            mWidth = sizeWidth;
        } else if (modeWidth == MeasureSpec.AT_MOST) {
            mWidth = Math.min(screenHeight, sizeWidth);
        }
        marginLeft = getMarginLeft();
        Log.e(MainActivity.TAG, "width: " + mWidth + "  height:  " + mHeight + "  marginLeft:  " + marginLeft);
        setMeasuredDimension(mWidth, mHeight);
    }


    public Paint getPaint() {

        return mPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        num = mBars.size();
        int left;
        int right;
        int value;
        int top;
        int Y = mHeight - marginBottom;
        for (int i = 0; i < num; i++) {
            Bar bar = mBars.get(i);
            height = bar.getHeightSize();
            Random random = new Random();
            int nextInt = random.nextInt(color.length - 1);
            mPaint.setColor(color[nextInt]);
            left = marginLeft + (childWidth + space) * i;
            right = left + childWidth;
            value = Y;
            top = value - value * height / default_height;
            mRf.set(left, top, right, value);
            Log.e(MainActivity.TAG, "left=="
                    + left + "\n" + "right=="
                    + right + "\n" + "value=="
                    + value + "\n" + "top=="
                    + top + "\n" + "height=="
                    + height + "\n" + "mHeight=="
                    + mHeight + "\n" + "marginBottom=="
                    + marginBottom + "\n");
            canvas.drawRect(mRf, mPaint);
        }
        //绘制x轴
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(50, Y, 100 + mWidth, Y, mPaint);
        //绘制y轴
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(50, Y, 50, 10, mPaint);
        //绘制y轴数字
        int oneH = Y / 5;
        for (int i = 0; i < ySteps.length; i++) {
            if (i == ySteps.length - 1) {
                canvas.drawText(ySteps[i], 10, Y + 10 - oneH * i, mPaint);
            } else {
                canvas.drawText(ySteps[i], 10, Y - oneH * i, mPaint);
            }

        }



        //绘制x轴横线
        mPaint.setColor(Color.WHITE);
        for (int i = 0; i < ySteps.length - 1; i++) {
            canvas.drawLine(50, oneH + oneH * i, mWidth, oneH + oneH * i, mPaint);
        }
        super.onDraw(canvas);
    }

    public BarGraphView setPaint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public BarGraphView setChildWidth(int childWidth) {
        this.childWidth = childWidth;
        return this;
    }

    public BarGraphView setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public void invalidate() {
        postInvalidate();
    }

    public void setBars(List<Bar> bars) {
        mBars = bars;
        postInvalidate();
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public BarGraphView setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    static class Bar {
        int heightSize;

        public Bar(int heightSize) {
            this.heightSize = heightSize;
        }

        public int getHeightSize() {
            return heightSize;
        }
    }
}
