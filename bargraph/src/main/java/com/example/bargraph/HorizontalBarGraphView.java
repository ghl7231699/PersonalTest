package com.example.bargraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by guhongliang on 2017/9/9.
 * 水平方向的柱形图
 */

public class HorizatalBarCharView extends View {
    private Paint mPaint;
    private Path mPath;
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
    private String[] men = {"金汉王通讯研发中心", "领智中心", "领智中心"};

    public HorizatalBarCharView(Context context) {
        this(context, null);
    }

    public HorizatalBarCharView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initParam();
    }

    public HorizatalBarCharView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initParam() {
        mRf = new Rect();
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText();
    }
}
