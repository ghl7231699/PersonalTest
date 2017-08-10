package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by guhongliang on 2017/8/8.
 */

public class PaintView extends View {
    private Paint mPaint;
    private float x;
    private float y;
    private Path mPath;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(1);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float hisX = event.getHistoricalX(i);
                    float hisY = event.getHistoricalY(i);
                    mPath.lineTo(hisX, hisY);
                }
                break;
        }
        invalidate();
        return true;
    }
}
