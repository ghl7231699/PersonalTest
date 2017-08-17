package com.example.liangge.rxjavatest.intef;

/**
 * Created by guhongliang on 2017/8/16.
 * 下载监听接口
 */

public interface ProgressListener {
    /**
     * @param readLength    已经下载或上传字节数
     * @param contentLength 总字节数
     * @param done          下载是否完成
     */
    void update(long readLength, long contentLength, boolean done);
}
