package com.example.bargraph;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by guhongliang on 2017/9/8.
 */

public class BarCharGroupView extends LinearLayout {
    private boolean orientation = false;//默认为水平方向
    private int mWidth;//获取到控件的宽度
    private int mHeight;
    private Activity mActivity;
    private int paddingLeft;//距离左边的距离
    private int paddingRight;//距离右边的距离
    private int paddingTop;//距离右边的距离
    private int paddingBottom;//距离右边的距离
    private int num;//加入的子view的个数
    private int viewMargin = 10;//相邻两个view之间的距离
    MarginLayoutParams layoutParams;


    private Path mPath;
    private String[] ySteps = new String[]{"0", "20k", "40k", "60k", "80k", "100k"};


    private Paint mPaint;

    public BarCharGroupView(Context context) {
        this(context, null);
    }

    public BarCharGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager;
        if (orientation) {
            setOrientation(VERTICAL);
        } else {
            setOrientation(HORIZONTAL);
        }
        if (context instanceof Activity) {
            mActivity = (Activity) context;
            windowManager = mActivity.getWindowManager();
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            mWidth = metrics.widthPixels;
//            mHeight = metrics.heightPixels;
        }
        mPaint = new Paint();
//        mPaint.setColor(R.color.imaginary_line);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int screenWidth = Utils.getScreenWidth(getContext());
        int screenHeight = Utils.getScreenHeight(getContext());
        //计算出所有子view的宽、高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
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
        Log.e(MainActivity.TAG, "mWidth: " + mWidth + "  mHeight:  " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        num = getChildCount();
        //获取到每个子view的宽度
        int viewWidth = (mWidth - paddingLeft - paddingRight - viewMargin * (num - 1)) / num;
        for (int i = 0; i < num; i++) {
            View childAt = getChildAt(i);
            int childWidth = childAt.getMeasuredWidth();
            int childHeight = childAt.getMeasuredHeight();
            Log.e(MainActivity.TAG, "childWidth: " + childWidth + "  childHeight:  " + childHeight);
            layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            int cl = viewWidth * i + layoutParams.leftMargin * (i + 1);
            int ct = mHeight - childHeight;
            int cr = cl + viewWidth * (i + 1);
            int cb = mHeight-10;
            Log.e(MainActivity.TAG, "cl=="
                    + cl + "\n" + "ct=="
                    + ct + "\n" + "cr=="
                    + cr + "\n" + "cb=="
                    + cb);
            childAt.layout(cl, ct, cr, cb);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int y = mHeight;
        canvas.drawLine(0, y - 10, mWidth, y - 10, mPaint);
        //画虚线
        int oneH = y / (ySteps.length - 1);
        drawImaginaryLine(canvas, oneH);
        drawYText(canvas, y, oneH);
    }


    private void drawYText(Canvas canvas, int y, int oneH) {
        for (int i = 0; i < ySteps.length; i++) {
            if (i == ySteps.length - 1) {
                canvas.drawText(ySteps[i], 10, y + 10 - oneH * i, mPaint);
            } else {
                canvas.drawText(ySteps[i], 10, y - oneH * i, mPaint);
            }

        }
    }

    private void drawImaginaryLine(Canvas canvas, int oneH) {

        mPaint.setColor(R.color.imaginary_line);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));
        for (int i = 0; i < ySteps.length - 1; i++) {
            mPath.reset();
            mPath.moveTo(50, oneH + oneH * i);
            mPath.lineTo(mWidth, oneH + oneH * i);
//            canvas.drawLine(50, oneH + oneH * i, mWidth, oneH + oneH * i, mPaint);
            canvas.drawPath(mPath, mPaint);
        }
    }
}
