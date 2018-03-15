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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：
 * 类描述：可拖动柱形图
 * 创建人：ghl
 * 创建时间：2018/3/15 下午2:49
 * 修改人：ghl
 * 修改时间：2018/3/15 下午2:49
 *
 * @version v1.0
 */

public class DragScrollView extends View {
    private Paint mTextPaint;//字体
    private Paint mLinePaint;//线条
    private Paint mRectPaint;//矩形
    private Path mPath;
    private int childWidth = Utils.dp2px(getContext(), 15);//条形图的宽度
    private int marginLeft = 10;//距离左边界的距离
    private int marginBottom = Utils.dp2px(getContext(), 14);
    private int marginTop = Utils.dp2px(getContext(), 20);
    private int marginRight = Utils.dp2px(getContext(), 81);
    private int space = Utils.dp2px(getContext(), 20);//相邻两个条形图之间的距离
    private int num = 1;//条形图的个数
    private int mWidth;//View的宽度
    private int mHeight;//View的高度
    private int default_height = 100;
    private int height;//条形图的高度

    private int lineCount = 6;

    private int textWidth;//文本宽度
    private int textMarginLeft = Utils.dp2px(getContext(), 22);//文本距离左边的距离

    private int textNum = 5;//每行显示的文字个数
    private int spaceY = 10;//大条形与小条形之间的距离
    private Rect mTextRf;//文本框矩形

    private Rect mRf;

    private String childName;//左侧名称
    private String childNum;//右侧数字
    private int childColor;
    private int childTextColor;

    private int standerH;
    private float scaling;//缩放比例

    private int max;//最大值

    public void setBars(List<HorizontalBarGraphView.HorizontalBar> bars) {
        mBars = bars;
        postInvalidate();
    }

    private List<HorizontalBarGraphView.HorizontalBar> mBars;

    public DragScrollView(Context context) {
        this(context, null);
    }

    public DragScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initParam(context, attrs);
    }

    public DragScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = View.MeasureSpec.getSize(heightMeasureSpec);
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int screenWidth = Utils.getScreenWidth(getContext());
        int screenHeight = Utils.getScreenHeight(getContext());
        int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        if (mode == View.MeasureSpec.EXACTLY || mode == View.MeasureSpec.UNSPECIFIED) {
            mHeight = size;
        } else if (mode == View.MeasureSpec.AT_MOST) {
            mHeight = Math.min(screenWidth, standerH);
        }
        if (modeWidth == View.MeasureSpec.EXACTLY || mode == View.MeasureSpec.UNSPECIFIED) {
            mWidth = sizeWidth;
        } else if (modeWidth == View.MeasureSpec.AT_MOST) {
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
        mTextPaint = new Paint();
        mRectPaint = new Paint();
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取条形图数量
        num = mBars.size();
        setSize();
        if (num > 0) {
            //获取距离上部的距离
            marginTop = (mHeight - childWidth * num - space * (num - 1)) / 2;
            int y = childWidth;
            //绘制左侧文字
            drawTextName(canvas, y);
            //虚线之间的距离
            int oneH = (mWidth - marginLeft - textWidth - textMarginLeft - marginRight) / (lineCount - 1);
            //实线开始绘制的起始坐标
            int startX = textWidth + marginLeft + textMarginLeft;
            //绘制矩形
            drawRect(canvas, y, startX);
            //绘制虚线
            drawImaginaryLine(canvas, oneH, startX);
        } else {

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
            HorizontalBarGraphView.HorizontalBar horizontalBar = mBars.get(i);
            height = horizontalBar.getHeight();
            int colors = horizontalBar.getColor();
            mRectPaint.setColor(colors);
            int top = y * i + space * i + marginTop;
            int bottom = top + y;
            //大矩形的长度
            int length = startX + (mWidth - startX - marginRight) * height / default_height;
            Log.e(MainActivity.TAG, "绘制坐标: " + length);
            //绘制大矩形
            mRf.set(startX, top, length, bottom);
            canvas.drawRect(mRf, mRectPaint);
            //绘制小条形
            int l = length + spaceY;
            mRf.set(l, top, l + spaceY, bottom);
            canvas.drawRect(mRf, mRectPaint);
            //设置字体大小
            mTextPaint.setTextSize(Utils.dp2px(getContext(), 14 * scaling));
            mTextPaint.setARGB(252, 106, 106, 119);
            childNum = horizontalBar.getTextContent();
            canvas.drawText(childNum, l + spaceY * 2, y * i + space * i + marginTop, mTextPaint);
            //绘制月/租金
            mTextPaint.setTextSize(Utils.dp2px(getContext(), 10 * scaling * scaling));
            mTextPaint.setColor(Color.argb(252, 158, 164, 175));
        }
    }

    /**
     * 绘制柱形图子标题
     *
     * @param canvas
     * @param y      单个条形图的宽度
     */
    private void drawTextName(Canvas canvas, int y) {
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setDither(true);
//        getContext().getResources().getColor(R.color.text_color);
//        mTextPaint.setColor(Color.argb(252, 106, 106, 119));
        mTextPaint.setColor(childTextColor);
        //设置字体大小
        mTextPaint.setTextSize(Utils.dp2px(getContext(), 12 * scaling));
        for (int i = 0; i < num; i++) {
            childName = mBars.get(i).getTextItem();
            //获取文本内容的大小
            int length = childName.length();
            mTextPaint.getTextBounds(childName, 0, length, mTextRf);
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            int textHeight = (int) ((-fontMetrics.ascent - fontMetrics.descent) / 2);
            //获取文本总宽度
            float measureText = mTextPaint.measureText(childName);
            float perTextW = measureText / length;
            //实际分配给文本的宽度
            int allocate = (int) (perTextW * textNum);
            //将想要的值赋给文本，固定文本宽度
            textWidth = allocate;
            canvas.drawText(childName, 0, textHeight / 2 + space * i + marginTop, mTextPaint);
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
                canvas.drawLine(xl, 0, xl, mHeight, mLinePaint);
            } else {
                mPath.reset();
                int x = xl + oneH * i;
                mPath.moveTo(x, 0);
                Log.e(MainActivity.TAG, "oneH " + x);
                mPath.lineTo(x, mHeight);
                canvas.drawPath(mPath, mLinePaint);
            }
        }

    }

    /**
     * 所有的赋值方法
     */
    public DragScrollView setChildWidth(int childWidth) {
        this.childWidth = Utils.dp2px(getContext(), childWidth);
        return this;
    }

    public DragScrollView setMarginLeft(int marginLeft) {
        this.marginLeft = Utils.dp2px(getContext(), marginLeft);
        return this;
    }

    public DragScrollView setMarginBottom(int marginBottom) {
        this.marginBottom = Utils.dp2px(getContext(), marginBottom);
        return this;
    }

    public DragScrollView setMarginTop(int marginTop) {
        this.marginTop = Utils.dp2px(getContext(), marginTop);
        return this;
    }

    public DragScrollView setMarginRight(int marginRight) {
        this.marginRight = Utils.dp2px(getContext(), marginRight);
        return this;
    }

    public DragScrollView setSpace(int space) {
        this.space = Utils.dp2px(getContext(), space);
        return this;
    }

    public DragScrollView setHeight(int height) {
        this.height = Utils.dp2px(getContext(), height);
        return this;
    }

    public DragScrollView setLineCount(int lineCount) {
        this.lineCount = Utils.dp2px(getContext(), lineCount);
        return this;
    }

    public DragScrollView setChildName(String childName) {
        this.childName = childName;
        return this;
    }

    public DragScrollView setChildNum(String childNum) {
        this.childNum = childNum;
        return this;
    }

    public DragScrollView setChildColor(int childColor) {
        this.childColor = childColor;
        return this;
    }

    public DragScrollView setChildTextColor(int childTextColor) {
        this.childTextColor = childTextColor;
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
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
