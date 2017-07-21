package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by guhongliang on 2017/7/20.
 */

public class PercentView extends View {
    private final static String TAG = PercentView.class.getSimpleName();
    private Paint mPaint;
    private RectF mRectFm;
    private int p;
    private int t;

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectFm = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getSize(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "onMeasure: ---widthMode" + widthMode);
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
        }
        Log.e(TAG, "onMeasure: ---widthSize" + widthSize);
        Log.e(TAG, "onMeasure: ---heightMode" + heightMode);
        Log.e(TAG, "onMeasure: ---heightSize" + heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        //FILL 填充 STROKE 描边 FILL_AND_STROKE 描边并填充
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int width = getWidth();
        int height = getHeight();
        Log.e(TAG, "onDraw:" + width + height);
        int radius = width / 4;
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);
        mPaint.setColor(Color.BLUE);
        mRectFm.set(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
        Log.e(TAG, "onDraw: p " + p);
        Log.e(TAG, "onDraw: t " + t);
        canvas.drawArc(mRectFm, -90, 360 * p / 100, true, mPaint);
        String text = p + "%";
        float v = mPaint.measureText(text, 0, text.length());
        mPaint.setTextSize(20);
        mPaint.setColor(Color.RED);
        canvas.drawText(text, width / 2 - v / 2, height / 2, mPaint);
    }

    public void getProgress(int p, int t) {
        if (p < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.p = p;
        this.t = t;
        postInvalidate();
    }
}
