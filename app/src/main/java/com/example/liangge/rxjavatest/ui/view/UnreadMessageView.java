package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by guhongliang on 2018/1/4.
 */

public class UnreadMessageView extends View {
    private Paint mPaint;
    private Context mContext;
    private int paddingLeft = 20;
    private int paddingTop = 20;
    private int paddingRight = 20;
    private int paddingBottom = 20;
    private int width;
    private int height;
    private String content;//文本内容
    private String num;//数字
    private int color;//字体颜色

    public void setColor(int color) {
        this.color = color;
    }

    public void setContent(String content) {
        this.content = content;
        postInvalidate();
    }

    public void setNum(String num) {
        this.num = num;
        postInvalidate();
    }

    public UnreadMessageView(Context context) {
        this(context, null);
    }

    public UnreadMessageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnreadMessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWidth(width, widthMeasureSpec);
        height = measureHeight(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        RectF rectF = new RectF();
        rectF.set(0, 0, width, height);
        canvas.drawRect(rectF, mPaint);

        mPaint.setColor(color);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(40);
        float textWidth = mPaint.measureText(content);
        Rect rect = measureMost(content);
        int textY = paddingTop + (height - paddingTop - paddingBottom) / 2;
//        int textY = height / 2;

        int radius = rect.height() / 2;
        int textX = (int) (paddingLeft + (width - textWidth - paddingLeft - 2 * radius - 5 - paddingRight) / 2);
        canvas.drawText(content, textX, textY + rect.height() / 2, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        float centerX = textX + textWidth + radius + 5;
        canvas.drawCircle(centerX, textY, radius, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(20);
        float v = mPaint.measureText(num);
        Rect h = measureMost(num);
        int y = textY + h.height() / 2;
        int x = (int) (centerX - v / 2);
        canvas.drawText(num, x, y, mPaint);

        super.onDraw(canvas);
    }

    @NonNull
    private Rect measureMost(String content) {
        Rect rect = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), rect);
        return rect;
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewWidth", "---speSize = " + specSize + "");
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e("YViewWidth", "---speMode = UNSPECIFIED");
//                defaultWidth = Math.max(defaultWidth, specSize);
                defaultWidth = (paddingLeft + measureMost(content).width() + paddingRight + 10) * 2;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                Log.e("YViewWidth", "---speMode = EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                defaultWidth = (paddingLeft + measureMost(content).width() + paddingRight + 10) * 2;
                Log.e("YViewWidth", "---speMode = AT_MOST");
                break;
        }
        Log.e("YViewWidth", "---defaultWidth = " + defaultWidth + "");
        return defaultWidth;
    }

    private int measureHeight(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("YViewHeight", "---speSize = " + specSize + "");
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e("YViewHeight", "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                Log.e("YViewHeight", "---speMode = EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                defaultWidth = measureMost(content).height() + paddingTop + paddingBottom;
                Log.e("YViewHeight", "---speMode = AT_MOST");
                break;
        }
        Log.e("YViewWidth", "---defaultWidth = " + defaultWidth + "");
        return defaultWidth;

    }
}
