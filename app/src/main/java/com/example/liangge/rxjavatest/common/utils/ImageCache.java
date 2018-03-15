package com.example.liangge.rxjavatest.common.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 类名称：
 * 类描述：图片缓存类
 *
 * @version v1.0
 */

public class ImageCache {
    //图片缓存
    LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
        initImageCache();
    }

    private void initImageCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取内存的四分之一作为缓存
        final int maxSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        return mImageCache.get(url);
    }
}
