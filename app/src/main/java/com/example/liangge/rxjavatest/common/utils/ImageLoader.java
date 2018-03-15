package com.example.liangge.rxjavatest.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类名称：
 * 类描述：图片加载类
 * 创建人：ghl
 * 创建时间：2018/3/10 下午6:26
 * 修改人：ghl
 * 修改时间：2018/3/10 下午6:26
 *
 * @version v1.0
 */

public class ImageLoader {
        //图片缓存
    ImageCache mImageCache=new ImageCache();
    //线程池，线程池的数量为cpu的数量
    ExecutorService es= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

}
