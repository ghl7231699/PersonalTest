package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.liangge.rxjavatest.R;

/**
 * Created by guhongliang on 2017/7/27.
 */

public class RoundImage extends ImageView {
    private Paint mPaint;
    private static final int DEFAULT_ROUND_CORNER = 10;//默认圆角半径
    /**
     * 图片类型，为圆形还是圆角
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    /**
     * 圆角大小
     */
    private int mBorderRadius;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 3x3矩阵 用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRect;

    public RoundImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImage);
        mBorderRadius = a.getDimensionPixelSize(R.styleable.RoundImage_borderRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ROUND_CORNER,
                        getResources().getDisplayMetrics()));//获取圆角大小，默认为10
        type = a.getInt(R.styleable.RoundImage_type, TYPE_CIRCLE);
        a.recycle();
    }

    public RoundImage(Context context) {
        super(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 如果类型为圆形，则强制view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getDrawable() == null) {
            return;
        }
        initShader();
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRect, mBorderRadius, mBorderRadius, mPaint);
        } else
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //圆角图片的范围
        if (type == TYPE_ROUND) {
            mRect = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * 初始化bitmapShader
     */
    private void initShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmap = drawableToBm(drawable);
        //将bitmap作为着色器，在指定的区域内绘制bmp
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (type == TYPE_CIRCLE) {
            //选择较小值为准
            int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = mWidth * 1.0f / min;
        } else if (type == TYPE_ROUND) {
            if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight())) {
                //如果图片的宽高比与view的宽高不匹配，则计算出需要压缩的比例，缩放后的图片一定要大于view的宽高，故选择较大值
                scale = Math.max(getWidth() * 1.0f
                        / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
            }
        }
        //shader的变换矩阵，主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        //设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        //设置shader
        mPaint.setShader(mBitmapShader);
    }

    /**
     * drawable转换成bitmap
     *
     * @param d
     * @return
     */
    private Bitmap drawableToBm(Drawable d) {
        if (d instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) d;
            return bd.getBitmap();
        }
        int width = d.getIntrinsicWidth();
        int height = d.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, width, height);
        d.draw(canvas);
        return bitmap;
    }
}
