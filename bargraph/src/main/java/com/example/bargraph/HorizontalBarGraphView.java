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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2017/9/9.
 * 水平方向的柱形图
 */

public class HorizontalBarGraphView extends View {

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
    private int num = 1;//条形图的个数
    private int mWidth;//View的宽度
    private int mHeight;//View的高度
    private int default_height = 100;
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
    private int[] color = {Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK, Color.GREEN, Color.GRAY};
    private String[] menu = {"金汉王通讯研发中心", "领智中心", "领智中心"};
    private String[] numText = {"15000", "12000", "12000"};
    private String perText = "月租金（元/月）";
    private int type;//类型 用于绘制不同的顶部

    private String childName;//左侧名称
    private String childNum;//右侧数字
    private int childColor;
    private int childTextColor;

    private int standerH;
    private float scaling;//缩放比例

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
        childColor = typedArray.getColor(R.styleable.HorizontalBarGraphView_child_color, Color.BLACK);
        childTextColor = typedArray.getColor(R.styleable.HorizontalBarGraphView_child_text_name_color, Color.RED);
        standerH = typedArray.getDimensionPixelSize(R.styleable.HorizontalBarGraphView_height_stander, Utils.dp2px(getContext(), 124));

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取条形图数量
        num = mBars.size();
        setSize();
        drawTop(canvas);
        if (num > 0) {
//            //获取每个条形图之间的距离
//            space = (mHeight - marginTop - marginBottom - childWidth * (num - 1)) / num;
            space = (mHeight - marginTop - Utils.dp2px(getContext(), 5) - marginBottom - childWidth * (num - 1)) / num;
            //获取距离上部的距离
//            marginTop = (mHeight - childWidth * num - space * (num - 1)) / 2;
            int y = childWidth;
            //绘制左侧文字
            drawTextName(canvas, y);
            //虚线之间的距离
            int oneH = (mWidth - marginLeft - textWidth - textMarginLeft - textMarginRight - marginRight) / (lineCount - 1);
            //实线开始绘制的起始坐标
            int startX = textWidth + marginLeft + textMarginLeft + textMarginRight;
            //绘制矩形
            drawRect(canvas, y, startX);
            //绘制虚线
            drawImaginaryLine(canvas, oneH, startX);
        } else {

        }
    }

    /**
     * 绘制顶部视图
     */
    private void drawTop(Canvas canvas) {
        if (type == 0) {
            String s1 = "激活量";
            mTextPaint.setColor(Color.parseColor("#FF6A6A77"));
            //设置字体大小
            mTextPaint.setTextSize(Utils.dp2px(getContext(), 11));

            //获取文本内容的大小
            int text = s1.length();
            mTextPaint.getTextBounds(s1, 0, text, mTextRf);
            int width = mTextRf.width();
            int height = mTextRf.height();
            int le = Utils.dp2px(getContext(), 10);
            int i = Utils.dp2px(getContext(), 5);
            int itemW = width + i + le;
            //第一个item坐标
            int x = (mWidth - itemW * 3 - topMargin * 2) / 2;
            mRectPaint.setColor(Color.parseColor("#EE8F52"));
            //todo
            mRf.set(x, marginTop, x + le, marginBottom);
            canvas.drawRect(mRf, mRectPaint);


            canvas.drawText(s1, x + le * 2, marginTop, mTextPaint);
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
    private void drawRect(Canvas canvas, int y, int startX) {
        mRectPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < num; i++) {
            HorizontalBar horizontalBar = mBars.get(i);
            height = horizontalBar.getHeight();
            int height2 = 10;
            int height3 = 6;
            int colors = horizontalBar.getColor();
            mRectPaint.setColor(Color.parseColor("#EE8F52"));
            int top = y * i + space * i + marginTop + Utils.dp2px(getContext(), 5);
            int bottom = top + y;
            //矩形的右边界坐标
            int l1 = startX + (mWidth - startX - marginRight) * height / default_height;
            int l2 = l1 + (mWidth - startX - marginRight) * height2 / default_height;
            int l3 = l2 + (mWidth - startX - marginRight) * height3 / default_height;
            Log.e(MainActivity.TAG, "绘制坐标: " + l1);
            //绘制第一个矩形
            mRf.set(startX, top, l1, bottom);
            canvas.drawRect(mRf, mRectPaint);

            mRectPaint.setColor(Color.parseColor("#F4C65F"));
            mRf1.set(l1, top, l2, bottom);
            canvas.drawRect(mRf1, mRectPaint);

            mRectPain3.setColor(Color.parseColor("#F8E37D"));
            mRf2.set(l2, top, l3, bottom);
            canvas.drawRect(mRf2, mRectPain3);
            //设置字体大小
            mTextPaint.setTextSize(Utils.dp2px(getContext(), 10));
            mTextPaint.setColor(Color.parseColor("#FFFFFF"));
            childNum = String.valueOf(horizontalBar.getHeight());
            //获取文本内容的大小
            int text = childName.length();
            mTextPaint.getTextBounds(childName, 0, text, mTextRf);
            int width = mTextRf.width();
            int height = mTextRf.height();
            canvas.drawText(childNum, (startX + l1 - width) / 2, top + y - height / 2, mTextPaint);
            canvas.drawText(String.valueOf(height2), (l2 + l1 - width) / 2, top + y - height / 2, mTextPaint);
            canvas.drawText(String.valueOf(height3), (l3 + l2 - width) / 2, top + y - height / 2, mTextPaint);
        }
    }

    /**
     * 绘制柱形图子标题
     *
     * @param canvas
     * @param y      单个条形图的宽度
     */
    private void drawTextName(Canvas canvas, int y) {
        String max = "";
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setDither(true);
        mTextPaint.setColor(Color.parseColor("#FF6A6A77"));
        //设置字体大小
        mTextPaint.setTextSize(Utils.dp2px(getContext(), 11));
        for (HorizontalBar bar : mBars
                ) {
            String textContent = bar.getTextItem();
            if (textContent.length() > max.length()) {
                max = textContent;
            }
        }
        float measureText = mTextPaint.measureText(max);
        textWidth = (int) measureText;
        for (int i = 0; i < num; i++) {
            int top = y * i + space * i + marginTop + Utils.dp2px(getContext(), 5);
            childName = mBars.get(i).getTextItem();
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

        mLinePaint.setColor(R.color.imaginary_line);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{5, 1}, 0));

        for (int i = 0; i < lineCount; i++) {
            if (i == 0) {
                mLinePaint.setColor(R.color.full_line_color);
                canvas.drawLine(xl, marginTop + Utils.dp2px(getContext(), 2), xl, mHeight, mLinePaint);
            } else {
                mPath.reset();
                int x = xl + oneH * i;
                mPath.moveTo(x, marginTop + Utils.dp2px(getContext(), 2));
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

    public HorizontalBarGraphView setChildColor(int childColor) {
        this.childColor = childColor;
        return this;
    }

    public HorizontalBarGraphView setChildTextColor(int childTextColor) {
        this.childTextColor = childTextColor;
        return this;
    }

    public int getDefault_height() {
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

    private float leftMoving;
    private float lastPointY;
    private float movingLeftThisTime = 0.0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
//                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getRawY();
                movingLeftThisTime = lastPointY - y;

                leftMoving += movingLeftThisTime;
                lastPointY = y;

                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                lastPointY = event.getRawY();
                break;
            default:
                return super.onTouchEvent(event);
        }
        return true;
    }

    static class HorizontalBar {
        private int height;//单个条形的长度
        private int color;//单个条形的颜色
        private String textContent;//右侧数字
        private String textItem;//左侧文本

        public HorizontalBar(int height, int color, String textContent, String textItem) {
            this.height = height;
            this.color = color;
            this.textContent = textContent;
            this.textItem = textItem;
        }


        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getColor() {
            return color;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getTextItem() {
            return textItem;
        }

        public void setTextItem(String textItem) {
            this.textItem = textItem;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
