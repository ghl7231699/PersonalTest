package com.example.bargraph;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 类名称：HouseReportView
 * 类描述：房源报告view
 * 创建人：ghl
 * 创建时间：2018/3/29 下午2:30
 * 修改人：ghl
 * 修改时间：2018/3/29 下午2:30
 *
 * @version v1.0
 */

public class HouseReportView extends View {
    public static final int NUMBER = 1;
    public static final int TXT = 2;
    Context mContext;
    private Paint mPaint;
    private String title, total, large, small, txt, num;
    private int mHeight, mWidth;//控件的宽、高
    private int defaultHeight = ScreenUtil.dp2px(50);//默认高度
    private int defaultWidth;//
    private Rect mRect;

    private int style;//样式

    public HouseReportView(Context context) {
        this(context, null);
    }

    public HouseReportView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseReportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mContext = context;
        mRect = new Rect();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HouseReportView);
        title = typedArray.getString(R.styleable.HouseReportView_title);
        total = typedArray.getString(R.styleable.HouseReportView_total);
        large = typedArray.getString(R.styleable.HouseReportView_large);
        txt = typedArray.getString(R.styleable.HouseReportView_txt);
        num = typedArray.getString(R.styleable.HouseReportView_num);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        mPaint.setTextSize(ScreenUtil.dp2px(15));
        int i = measureHeight(total, mPaint) * 9;
        defaultHeight = ScreenUtil.dp2px(mContext, 10) + i;
        defaultWidth = screenWidth / 3;
        if (mode == MeasureSpec.EXACTLY) {
            mHeight = size;
        }
        if (mode == MeasureSpec.AT_MOST) {
            mHeight = defaultHeight;
        }
        if (mode == MeasureSpec.UNSPECIFIED) {
            mHeight = defaultHeight;
        }
        if (modeWidth == MeasureSpec.EXACTLY) {
            mWidth = sizeWidth;
        }
        if (mode == MeasureSpec.UNSPECIFIED) {
            mWidth = defaultWidth;
        }
        if (modeWidth == MeasureSpec.AT_MOST) {
            mWidth = defaultWidth;
        }
        setMeasuredDimension(mWidth, mHeight);
        Log.e("HouseReportView", "width:" + mWidth + "\n heigt:" + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1.0f);
        mPaint.setAntiAlias(true);

        mPaint.setColor(mContext.getResources().getColor(R.color.black));
        mPaint.setTextSize(ScreenUtil.dp2px(14));
        if (!TextUtils.isEmpty(title)) {
            int height = measureHeight(title, mPaint);
            int padding = ScreenUtil.dp2px(10);
            if (!TextUtils.isEmpty(large)) {
                padding = (mHeight - height - measureHeight(total, mPaint) - measureHeight(large, mPaint)) / 4;
            } else if (!TextUtils.isEmpty(num)) {
                padding = (mHeight - height - measureHeight(total, mPaint) - measureHeight(num, mPaint)) / 4;
            }

            canvas.drawText(title, 10, padding, mPaint);

            if (!TextUtils.isEmpty(total)) {
                //总量
                mPaint.setColor(mContext.getResources().getColor(R.color.orange));
                mPaint.setTextSize(ScreenUtil.dp2px(16));
                canvas.drawText(total, 10, padding * 2 + height, mPaint);
            }

            mPaint.setColor(mContext.getResources().getColor(R.color.color_999999));
            mPaint.setTextSize(ScreenUtil.dp2px(12));
            if (style == NUMBER) {
                //最下层文本
                String content = "大" + large + "," + "小" + small;
                canvas.drawText(content, 10, padding * 3 + height * 2, mPaint);
            }
            if (style == TXT) {
                mPaint.setColor(mContext.getResources().getColor(R.color.color_999999));
                mPaint.setTextSize(ScreenUtil.dp2px(12));
                //最下层文本
                String content = txt + num;
                canvas.drawText(txt, 10, padding * 3 + height * 2, mPaint);
                if (num.contains("↑")) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    String substring = num.substring(1);
                    float left = mPaint.measureText(txt);
                    int top = measureHeight(txt, mPaint);
                    mRect.set((int) (10 + left), top, (int) (left + 10 + bitmap.getWidth()), top + bitmap.getHeight());
//                    mRect.set(20, 30, 50, 50);
                    canvas.drawBitmap(bitmap, null, mRect, mPaint);
                    canvas.drawText(substring, mRect.width(), padding * 3 + height * 2, mPaint);

                }
            }
        }
    }

    private int measureHeight(String content, Paint paint) {
        if (TextUtils.isEmpty(content)) {
            return 0;
        }
        int length = content.length();
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, length, bounds);
        return bounds.height();
    }

    private int measureWidth(Paint paint) {
        int length;
        String s;
        String content = large + small;
        if (title.length() > content.length()) {
            length = title.length();
            s = title;
        } else {
            length = content.length();
            s = content;
        }
        if (length < total.length()) {
            length = total.length();
            s = total;
        }
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, length, bounds);
        return bounds.width();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setSmall(String small) {
        this.small = small;
        invalidate();
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setContent(String txt) {
        this.txt = txt;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
