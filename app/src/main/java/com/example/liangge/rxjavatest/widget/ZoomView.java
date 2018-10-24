package com.example.liangge.rxjavatest.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * 类描述：手势缩放view
 * <p>
 * 支持放大、缩小、裁剪
 * </p>
 * 创建人：ghl
 * 创建时间：2018/9/26
 *
 * @version v1.0
 */

public class ZoomView extends ImageView {
    // 初始化手势识别类
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private Matrix matrix;
    private Bitmap bitmap;
    private float[] matrixArray;
    private float setMinimumScale = 0.5f;//最小缩放系数
    private float maximumScale = 2.0f;//最大缩放系数
    private boolean canTranslate = false;
    private boolean isCheckLeftAndRight, isCheckTopAndBottom;
    private Activity mActivity;
    private boolean shortDown;//是否是短点击
    /**
     * 图片长度
     */
    private float mImageWidth;
    /**
     * 图片高度
     */
    private float mImageHeight;

    public ZoomView(Context context) {
        super(context);
        init();
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        matrixArray = new float[9];
        // 初始化缩放手势识别类，用于图片的缩放
        scaleGestureDetector = new ScaleGestureDetector(getContext(), onScaleGestureListener);
        // 初始化手势识别类，用于图片的拖动
        gestureDetector = new GestureDetector(getContext(), onGestureListener);
        setScaleType(ScaleType.FIT_CENTER);
    }

    // 缩放手势监听
    private ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            // 处理图片缩放
            scale(scaleGestureDetector);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            // 设为true，才能执行接下来的操作,onScale --> onScaleEnd
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            //获取本次缩放事件的缩放因子(缩放事件以onScale()返回值为基准，一旦该方法返回true，代表本次事件结束，要开启下次事件)
            float scale = scaleGestureDetector.getScaleFactor();
            matrix.postScale(scale, scale);
            invalidate();
        }
    };
    // 手势监听
    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            shortDown = true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (shortDown) {
                if (mActivity != null) {
                    mActivity.finish();
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
            // 移动
            if (canTranslate) {
                translate(distanceX, distanceY);
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    };

    // 设置缩放
    private void scale(ScaleGestureDetector detector) {
        // detector.getScaleFactor() 获取缩放因子，每次都是从1开始，缩小会小于1，放大会大于1
        float scale = detector.getScaleFactor();
        Log.e("scale: ", scale + "");
        // 当前图片矩阵的缩放，放大会增加，缩小会减少
        float currentScale = getScale();
        Log.e("current scale: ", currentScale + "");
        // 设置最小缩放
        if (currentScale * scale < setMinimumScale) {
            scale = setMinimumScale / currentScale;
        }
        // 设置最大缩放
        if (currentScale * scale > maximumScale) {
            scale = maximumScale / currentScale;
        }
        // 图片以焦点为中心，缩放，缩放比例为scale
        matrix.postScale(scale, scale, detector.getFocusX(), detector.getFocusY());
        mImageWidth = getWidth() * scale;
        mImageHeight = getHeight() * scale;

        canTranslate = true;
        // 更新view
        invalidate();
    }

    // Matrix.MSCALE_X x轴缩放比，Matrix.MSKEW_X x轴旋转角度
    private float getScale() {
        // 将矩阵赋值给matrixArray
        matrix.getValues(matrixArray);
        // 获取X轴缩放比
        float scale = matrixArray[Matrix.MSCALE_X];
        if (Math.abs(scale) <= 0.1) { // 处理最低缩放
            scale = matrixArray[Matrix.MSKEW_X];
        }
        return Math.abs(scale);
    }

    // 处理图片拖动功能
    private void translate(float distanceX, float distanceY) {
        RectF rectF = getMatrixRectF();
        isCheckLeftAndRight = isCheckTopAndBottom = true;
        // 如果宽度小于屏幕宽度，则禁止左右移动
        if (rectF.width() < getWidth()) {
            distanceX = 0;
            isCheckLeftAndRight = false;
        }
        // 如果高度小于屏幕高度，则禁止上下移动
        if (rectF.height() < getHeight()) {
            distanceY = 0;
            isCheckTopAndBottom = false;
        }
        matrix.postTranslate(-distanceX, -distanceY);
        checkMatrixBounds();
        setImageMatrix(matrix);
    }

    /**
     * 设置显示的图片路径  /storage/emulated/0/DCIM/Camera/IMG_20151002_080421_mh1466438438485.jpg
     * file:///storage/emulated/0/image/1537945803410.jpg不支持
     *
     * @param path
     * @param activity
     */

    public void setFilePath(Activity activity, String path) {
        mActivity = activity;
        Bitmap originalBitmap = BitmapFactory.decodeFile(path);
//        if (originalBitmap == null) {
//            return;
//        }
//        int width = originalBitmap.getWidth();
//        int height = originalBitmap.getHeight();
//        Bitmap bitmap = BitmapUtils.getMatriBitmap(path, Envi.screenWidth, Envi.screenHeight);
//        setBitmap(originalBitmap);
//        setBitmap(bitmap);
//        setImageBitmap(originalBitmap);
    }

    // 设置bitmap
    private void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        matrix.reset();
        centerImage(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bitmap == null) {
            return;
        }
        super.setImageBitmap(bm);
        this.bitmap = bm;
        matrix.set(getImageMatrix());
        float[] values = new float[9];
        matrix.getValues(values);
//        matrix.reset();
//        centerImage(bitmap.getWidth(), bitmap.getHeight());

        float dx = 0;
        float dy = 0;
        matrix.setTranslate(0, 0); // 图片的移动
        // 设置图片的缩放，ratio是缩放的比例; px,py是缩放的轴点。下面是以以图片中心为轴点的等比例缩放
        matrix.setScale(1, 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        //以set开头的会把之前的矩阵置为单位矩阵，然后在set，如果想要上面的几种方式一起操作，则需要使用pre或post开头的方法
        matrix.postTranslate(dx, dy);
    }

    /**
     * 令图片居中
     *
     * @param width  当前控件的宽度
     * @param height 当前控件的高度
     */
    private void centerImage(int width, int height) {
        if (width <= 0 || height <= 0 || bitmap == null) {
            return;
        }
        float widthRatio = 1.0f * height / this.bitmap.getHeight();
        float heightRatio = 1.0f * width / this.bitmap.getWidth();
        float ratio = Math.min(widthRatio, heightRatio);
        float dx = (width - this.bitmap.getWidth()) / 2;
        float dy = (height - this.bitmap.getHeight()) / 2;
        matrix.setTranslate(0, 0); // 图片的移动
        // 设置图片的缩放，ratio是缩放的比例; px,py是缩放的轴点。下面是以以图片中心为轴点的等比例缩放
        matrix.setScale(ratio, ratio, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        //以set开头的会把之前的矩阵置为单位矩阵，然后在set，如果想要上面的几种方式一起操作，则需要使用pre或post开头的方法
        matrix.postTranslate(dx, dy);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleGestureDetector.onTouchEvent(event);
        result = gestureDetector.onTouchEvent(event) || result;
        return result || super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        // 绘制时，将bitmap进行绘制在控件上
//        if (bitmap != null) {
//            canvas.drawBitmap(bitmap, matrix, null);
//        }
    }

    // 裁剪图片
    public Bitmap crop(Rect frame) {
        float scale = getScale();
        float[] src = new float[]{frame.left, frame.top};
        float[] desc = new float[]{0, 0};
        Matrix invertedMatrix = new Matrix();
        this.matrix.invert(invertedMatrix);
        invertedMatrix.mapPoints(desc, src);
        Matrix matrix = new Matrix();
        int width = (int) (frame.width() / scale);
        int height = (int) (frame.height() / scale);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Bitmap originalBitmap = this.bitmap;
        matrix.postTranslate(-desc[0], -desc[1]);
        canvas.drawBitmap(originalBitmap, matrix, null);
        return bitmap;
    }

    /**
     * 移动时，进行边界判断，主要判断宽或高大于屏幕的，防止出现屏幕白边
     */
    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        matrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     */
    private RectF getMatrixRectF() {
        RectF rect = new RectF();
        rect.set(0, 0, mImageWidth, mImageHeight);
        matrix.mapRect(rect);
        return rect;
    }
}
