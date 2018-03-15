package com.example.liangge.rxjavatest.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 类名称：
 * 类描述：图片处理工具
 * 创建人：ghl
 * 创建时间：2018/3/10 下午6:40
 * 修改人：ghl
 * 修改时间：2018/3/10 下午6:40
 *
 * @version v1.0
 */

public class BitmapUtils {
    public static Bitmap getScaleBitmap(byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true 则表示只测量图片的边框，不会给图片分配实际内存
        options.inJustDecodeBounds = true;
        //缩小为原来的四分之一
        options.inSampleSize = 4;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }
}
