package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by guhongliang on 2017/9/6.
 */

public class DashedLine extends View {
    private Paint mPaint;
    private Path mPath;

    public DashedLine(Context context) {
        this(context, null);
    }

    public DashedLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashedLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int y = getHeight() / 2;
        mPath.reset();
        mPath.moveTo(0, y);
        mPath.lineTo(getWidth(), y);
        canvas.drawPath(mPath, mPaint);
    }
}
