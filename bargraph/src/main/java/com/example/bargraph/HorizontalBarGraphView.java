package com.example.bargraph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.bargraph.bean.HorizontalBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2017/9/9.
 * 水平方向的柱形图
 */

public class HorizontalBarGraphView extends View {

    private static final int DEFAULT_NUMBER = 8;

    private Paint mTextPaint;//字体
    private Paint mLinePaint;//线条
    private Paint mRectPaint;//矩形
    private Paint mRectPain2;//矩形
    private Paint mRectPain3;//矩形
    private Path mPath;
    private int childWidth = Utils.dp2px(getContext(), 15);//条形图的宽度
    private int marginLeft = Utils.dp2px(getContext(), 10);//距离左边界的距离
    private int marginBottom = Utils.dp2px(getContext(), 10);
    private int marginTop = Utils.dp2px(getContext(), 10);
    private int marginRight = Utils.dp2px(getContext(), 10);
    private int space = Utils.dp2px(getContext(), 20);//相邻两个条形图之间的距离
    private int num = 8;//条形图的个数
    private int mWidth;//View的宽度
    private int mHeight;//View的高度
    private int coefficient = 1000;//系数 系数越高 则坐标更准确
    private float default_height = 100.0f;
    private int height;//条形图的高度
    private int topMargin = Utils.dp2px(getContext(), 10);//顶部item之间的距离

    private int lineCount = 6;

    private int textWidth;//文本宽度
    private int textMarginLeft = Utils.dp2px(getContext(), 10);//文本距离左边的距离
    private int textMarginRight = Utils.dp2px(getContext(), 10);//文本距离左边的距离

    private int textNum = 5;//每行显示的文字个数
    private int spaceY = 10;//大条形与小条形之间的距离
    private Rect mTextRf;//文本框矩形

    private Rect mRf;
    private Rect mRf1;
    private Rect mRf2;
    private int type;//类型 用于绘制不同的顶部

    private String childName;//左侧名称
    private String childNum;//右侧数字

    private int topHeight;//顶部布局高度

    private String[] title;//顶部布局title
    private String[] colors;//顶部布局颜色

    private int standerH;
    private float scaling;//缩放比例
    private int mV;

    private int maxTop;//数据全部展示的最大高度
    private int minTop;//展示默认条数数据时的最大高度

    public void setBars(List<HorizontalBar> bars) {
        mBars = bars;
        postInvalidate();
    }

    private List<HorizontalBar> mBars;

    public HorizontalBarGraphView(Context context) {
        this(context, null);
    }

    public HorizontalBarGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initParam(context, attrs);
    }

    public HorizontalBarGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int screenWidth = Utils.getScreenWidth(getContext());
        int screenHeight = Utils.getScreenHeight(getContext());
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            mHeight = size;
        }
        if (mode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(screenWidth, standerH);
        }
        if (mode == MeasureSpec.UNSPECIFIED) {
            mHeight = Utils.dp2px(getContext(), 400);
        }
        if (modeWidth == MeasureSpec.EXACTLY || mode == MeasureSpec.UNSPECIFIED) {
            mWidth = sizeWidth;
        } else if (modeWidth == MeasureSpec.AT_MOST) {
            mWidth = Math.min(screenHeight, sizeWidth);
        }
        if (standerH > mHeight) {
            standerH = mHeight;
        }
        scaling = (float) mHeight / standerH;
        if (scaling > 1.0f) {
            scaling = 1.0f;
        }
        mWidth = (int) (mWidth * scaling);
        mHeight = (int) (mHeight * scaling);
        Log.e(MainActivity.TAG, "HorizontalBarGraphView" + "\n"
                + "width: " + mWidth
                + "  height:  " + mHeight
                + "  marginLeft:  " + marginLeft
                + "  缩放比例为  " + scaling
        );
        setMeasuredDimension(mWidth, mHeight);
    }

    private void initParam(Context context, AttributeSet attrs) {
        mRf = new Rect();
        mRf1 = new Rect();
        mRf2 = new Rect();
        mTextPaint = new Paint();
        mRectPaint = new Paint();
        mRectPain2 = new Paint();
        mRectPain3 = new Paint();
        mLinePaint = new Paint();
        mPath = new Path();
        mTextRf = new Rect();
        mBars = new ArrayList<>();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalBarGraphView);
        childWidth = typedArray.getDimensionPixelSize(R.styleable.HorizontalBarGraphView_child_width, Utils.dp2px(getContext(), 15));
        childName = typedArray.getString(R.styleable.HorizontalBarGraphView_child_text_name);
        childNum = typedArray.getString(R.styleable.HorizontalBarGraphView_child_text_num);
        standerH = typedArray.getDimensionPixelSize(R.styleable.HorizontalBarGraphView_height_stander, Utils.dp2px(getContext(), 124));

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawTop(canvas);
        setSize();
        //获取条形图数量 最多显示8个
        int number = mBars.size();
        if (number <= 8) {
            num = number;
        }
        //获取每个条形图之间的距离
//            space = (mHeight - marginTop - marginBottom - childWidth * (num - 1)) / num;
        space = (mHeight - marginTop - Utils.dp2px(getContext(), 5) - topHeight - marginBottom - childWidth * (num - 1)) / num;

        checkLeftMoving();
        Log.e(MainActivity.TAG, "移动距离" + mV);


        List<HorizontalBar> bars = scrollTo(num);

        //获取距离上部的距离
//            marginTop = (mHeight - childWidth * num - space * (num - 1)) / 2;
        int y = childWidth;
        //绘制左侧文字
        drawTextName(bars, canvas, y);
        //虚线之间的距离
        int oneH = (mWidth - marginLeft - textWidth - textMarginLeft - textMarginRight - marginRight) / (lineCount - 1);
        //实线开始绘制的起始坐标
        int startX = textWidth + marginLeft + textMarginLeft + textMarginRight;
        //绘制矩形
        drawRect(bars, canvas, y, startX);
        //绘制虚线
        drawImaginaryLine(canvas, oneH, startX);

    }

    /**
     * 绘制顶部视图
     */
    private void drawTop(Canvas canvas) {
        if (type == 0) {
            //设置字体大小
            mTextPaint.setTextSize(Utils.dp2px(getContext(), 10));
            for (int i = 0; i < 3; i++) {
                String txt = title[i];
                String color = colors[i];
                mTextPaint.setColor(Color.parseColor("#FF6A6A77"));
                //获取文本内容的大小
                int text = txt.length();
                mTextPaint.getTextBounds(txt, 0, text, mTextRf);
                int width = mTextRf.width();
                int height = mTextRf.height();
                int le = Utils.dp2px(getContext(), 6);
                int margin = Utils.dp2px(getContext(), 2);
                int itemW = width + margin + le;
                topHeight = height + topMargin * 2;


                //第一个item坐标
                int itemX = (mWidth - itemW * 3 - topMargin * 2) / 2;
                mRectPaint.setColor(Color.parseColor(color));
                //获取文本绘制的y坐标结束为止
                int end = topHeight / 2 + height / 2;
                //获取文本绘制的x轴起始坐标
                int start = itemX + itemW * i + marginTop * i + le * 2;

                canvas.drawText(txt, start, end, mTextPaint);

                //绘制小方块的y轴起始坐标
                int top = topHeight / 2 + le / 2 - height / 2;
                int right = itemX + itemW * i + marginTop * i + le;
                int bottom = topHeight / 2 + le * 3 / 2 - height / 2;
                int left = itemX + itemW * i + marginTop * i;
                mRf.set(left, top, right, bottom);
                canvas.drawRect(mRf, mRectPaint);
            }
        }
    }

    /**
     * 根据缩放比例，重新计算数值
     */
    private void setSize() {
        childWidth = (int) (childWidth * scaling);
        marginTop = (int) (marginTop * scaling);
        marginBottom = (int) (marginBottom * scaling);
        marginLeft = (int) (marginLeft * scaling);
        marginRight = (int) (marginRight * scaling);
        textMarginLeft = (int) (textMarginLeft * scaling);
        textMarginRight = (int) (textMarginRight * scaling);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     * @param y      单个条形图的宽度
     * @param startX 绘制矩形的x轴起始坐标
     */
    private void drawRect(List<HorizontalBar> bar, Canvas canvas, int y, int startX) {
        mRectPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < num; i++) {
            HorizontalBar horizontalBar = bar.get(i);
            int activityNo = horizontalBar.getActivityNo();
            int increasedNo = horizontalBar.getIncreasedNo();
            int claimNo = horizontalBar.getClaimNo();

            mRectPaint.setColor(Color.parseColor("#EE8F52"));
            int top = y * i + space * i + marginTop + Utils.dp2px(getContext(), 5) + topHeight;
            int bottom = top + y;
            //绘制柱形图的宽度
            int w = mWidth - startX - marginRight;
            //矩形的右边界坐标
            float v = activityNo / default_height;
            int l1 = (int) (startX + w * v);
            float v2 = increasedNo / default_height;
            int l2 = l1 + (int) (w * v2);
            float v3 = claimNo / default_height;
            int l3 = (int) (l2 + w * v3);
            //设置字体大小
            mTextPaint.setTextSize(Utils.dp2px(getContext(), 10));
            mTextPaint.setColor(Color.parseColor("#FFFFFF"));
            //获取文本内容的高度
            int text = String.valueOf(activityNo).length();
            mTextPaint.getTextBounds(String.valueOf(activityNo), 0, text, mTextRf);
            int height = mTextRf.height();
            //测量字体的宽度
            float activityNoText = mTextPaint.measureText(String.valueOf(activityNo));
            float increasedNoText = mTextPaint.measureText(String.valueOf(increasedNo));
            float claimNoNoText = mTextPaint.measureText(String.valueOf(claimNo));
            if (activityNo != 0) {
                //绘制第一个矩形
                Log.e(MainActivity.TAG, "绘制坐标: " + l1);
                mRf.set(startX, top, l1, bottom);
                canvas.drawRect(mRf, mRectPaint);
                canvas.drawText(String.valueOf(activityNo), startX + mRf.width() / 2 - activityNoText / 2, top + y - height / 2, mTextPaint);
            }
            if (increasedNo != 0) {
                mRectPaint.setColor(Color.parseColor("#F4C65F"));
                mRf1.set(l1, top, l2, bottom);
                canvas.drawRect(mRf1, mRectPaint);
                canvas.drawText(String.valueOf(increasedNo), l1 + (mRf1.width() - increasedNoText) / 2, top + y - height / 2, mTextPaint);
            }
            if (claimNo != 0) {
                mRectPain3.setColor(Color.parseColor("#F8E37D"));
                mRf2.set(l2, top, l3, bottom);
                canvas.drawRect(mRf2, mRectPain3);
                canvas.drawText(String.valueOf(claimNo), l2 + (mRf2.width() - claimNoNoText) / 2, top + y - height / 2, mTextPaint);
            }
        }
    }

    /**
     * 绘制柱形图子标题
     *
     * @param canvas
     * @param y      单个条形图的宽度
     */
    private void drawTextName(List<HorizontalBar> bar, Canvas canvas, int y) {
        String max = "";
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setDither(true);
        mTextPaint.setColor(Color.parseColor("#FF6A6A77"));
        //设置字体大小
        mTextPaint.setTextSize(Utils.dp2px(getContext(), 11));
        for (HorizontalBar b : bar
                ) {
            String textContent = b.getUserName();
            if (textContent.length() > max.length()) {
                max = textContent;
            }
        }
        float measureText = mTextPaint.measureText(max);
        textWidth = (int) measureText;
        for (int i = 0; i < num; i++) {
            int top = y * i + space * i + marginTop + Utils.dp2px(getContext(), 5) + topHeight;
            childName = bar.get(i).getUserName();
            //获取文本大小
            int length = childName.length();
            mTextPaint.getTextBounds(childName, 0, length, mTextRf);
            int width = mTextRf.width();
            int height = mTextRf.height();
            //获取文本总宽度
            canvas.drawText(childName, textWidth + marginLeft + textMarginLeft - textMarginRight - width, top + childWidth - height / 2, mTextPaint);
        }
    }

    /**
     * 虚线
     *
     * @param canvas
     * @param oneH   相邻虚线之间的距离
     * @param xl     x轴开始绘制的坐标
     */
    @SuppressLint("ResourceAsColor")
    private void drawImaginaryLine(Canvas canvas, int oneH, int xl) {

//        mLinePaint.setColor(Color.parseColor("#12313076"));
        mLinePaint.setColor(R.color.imaginary_line);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{5, 1}, 0));

        for (int i = 0; i < lineCount; i++) {
            if (i == 0) {
//                mLinePaint.setColor(Color.parseColor("#12313076"));
                mLinePaint.setColor(R.color.full_line_color);
                canvas.drawLine(xl, topHeight + Utils.dp2px(getContext(), 2), xl, mHeight, mLinePaint);
            } else {
                mPath.reset();
                int x = xl + oneH * i;
                mPath.moveTo(x, topHeight + Utils.dp2px(getContext(), 2));
                Log.e(MainActivity.TAG, "oneH " + x);
                mPath.lineTo(x, mHeight);
                canvas.drawPath(mPath, mLinePaint);
            }
        }

    }

    /**
     * 所有的赋值方法
     */
    public HorizontalBarGraphView setChildWidth(int childWidth) {
        this.childWidth = Utils.dp2px(getContext(), childWidth);
        return this;
    }

    public HorizontalBarGraphView setMarginLeft(int marginLeft) {
        this.marginLeft = Utils.dp2px(getContext(), marginLeft);
        return this;
    }

    public HorizontalBarGraphView setMarginBottom(int marginBottom) {
        this.marginBottom = Utils.dp2px(getContext(), marginBottom);
        return this;
    }

    public HorizontalBarGraphView setMarginTop(int marginTop) {
        this.marginTop = Utils.dp2px(getContext(), marginTop);
        return this;
    }

    public HorizontalBarGraphView setMarginRight(int marginRight) {
        this.marginRight = Utils.dp2px(getContext(), marginRight);
        return this;
    }

    public HorizontalBarGraphView setSpace(int space) {
        this.space = Utils.dp2px(getContext(), space);
        return this;
    }

    public HorizontalBarGraphView setHeight(int height) {
        this.height = Utils.dp2px(getContext(), height);
        return this;
    }

    public HorizontalBarGraphView setLineCount(int lineCount) {
        this.lineCount = Utils.dp2px(getContext(), lineCount);
        return this;
    }

    public HorizontalBarGraphView setChildName(String childName) {
        this.childName = childName;
        return this;
    }

    public HorizontalBarGraphView setChildNum(String childNum) {
        this.childNum = childNum;
        return this;
    }

    public float getDefault_height() {
        return default_height;
    }

    public HorizontalBarGraphView setDefault_height(int default_height) {
        this.default_height = default_height;
        return this;
    }

    public HorizontalBarGraphView setType(int type) {
        this.type = type;
        return this;
    }

    public String[] getTitle() {
        return title;
    }

    public HorizontalBarGraphView setTitle(String[] title) {
        this.title = title;
        return this;
    }

    public String[] getColors() {
        return colors;
    }

    public HorizontalBarGraphView setColors(String[] colors) {
        this.colors = colors;
        return this;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
////                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float y = event.getRawY();
//                movingLeftThisTime = lastPointY - y;
//
//                int size = mBars.size();
//                if (Math.abs(mV) > size - num) {
//                    leftMoving = (size - num) * childWidth;
//                } else {
////                    movingLeftThisTime = Math.abs(mV) * childWidth;
//                    leftMoving += movingLeftThisTime;
//                }
//                lastPointY = y;
//
//                invalidate();
//                break;
//            case MotionEvent.ACTION_DOWN:
//                lastPointY = event.getRawY();
//                break;
//            default:
//                return super.onTouchEvent(event);
//        }
//        return true;
//    }


    @NonNull
    private List<HorizontalBar> scrollTo(int number) {
        mV = (int) (upMoving / (childWidth + space));
        if (number < DEFAULT_NUMBER) {
            return mBars;
        }
        if (mV > number - DEFAULT_NUMBER) {
            mV = number - DEFAULT_NUMBER;
        }
        if (mV < 0) {
            mV = 0;
        }
        List<HorizontalBar> bars = new ArrayList<>();
        int position = num + mV;//滑动结束时 当前的item显示的最大条数
        for (int i = mV; i < position; i++) {
            bars.add(mBars.get(i));
        }
        return bars;
    }

    private float upMoving;
    private float lastPointY;
    private float lastPointX;
    private float movingLeftThisTime = 0.0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mBars != null && !mBars.isEmpty()) {
            int size = mBars.size();
            if ((size > DEFAULT_NUMBER)) {
                getParent().requestDisallowInterceptTouchEvent(false);//父控件处理touch事件
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                new Thread(new SmoothScrollThread(movingLeftThisTime)).start();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getRawY();
                float x = event.getRawX();
                if (mBars != null && !mBars.isEmpty()) {
                    int size = mBars.size();
                    if (y - lastPointY < mHeight / 2 && (x - lastPointX < mWidth / 2) && (size > DEFAULT_NUMBER || size == DEFAULT_NUMBER)) {
                        getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);//父控件处理touch事件
                    }
                }
                movingLeftThisTime = lastPointY - y;
                upMoving += movingLeftThisTime;
                lastPointY = y;
                lastPointX = x;

                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                lastPointY = event.getRawY();
                lastPointX = event.getRawX();
//                if (lastPointY < mHeight && lastPointY > mHeight - topHeight - topMargin) {
//                    getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
//                } else {
//                    getParent().requestDisallowInterceptTouchEvent(false);//父控件处理touch事件
//                }
                break;
            default:
                return super.onTouchEvent(event);
        }
        return true;
    }


    private void checkLeftMoving() {
        if (upMoving < 0) {
            upMoving = 0;
        }

        if (upMoving > (maxTop - minTop)) {
            upMoving = maxTop - minTop;
        }
    }

    private class SmoothScrollThread implements Runnable {
        float lastMoving;
        boolean scrolling = true;

        private SmoothScrollThread(float lastMoving) {
            this.lastMoving = lastMoving;
            scrolling = true;
        }

        @Override
        public void run() {
            while (scrolling) {
                long start = System.currentTimeMillis();
                lastMoving = (int) (0.9f * lastMoving);
                upMoving += lastMoving;

                checkLeftMoving();
                postInvalidate();

                if (Math.abs(lastMoving) < 5) {
                    scrolling = false;
                }

                long end = System.currentTimeMillis();
                if (end - start < 200) {
                    try {
                        Thread.sleep(20 - (end - start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
