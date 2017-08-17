package com.example.liangge.rxjavatest.custombody;

import com.example.liangge.rxjavatest.bean.MessageEvent;
import com.example.liangge.rxjavatest.intef.ProgressListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by guhongliang on 2017/8/16.
 * retrofit 下载显示进度条body
 */

public class ProgressResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private ProgressListener mListener;
    private BufferedSource mBufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        mResponseBody = responseBody;
        mListener = listener;
    }

    public ProgressResponseBody(ResponseBody responseBody) {
        mResponseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += (bytesRead != -1 ? bytesRead : 0);
                mListener.update(totalBytesRead, mResponseBody.contentLength(), bytesRead != -1);
                EventBus.getDefault().post(new MessageEvent(mResponseBody.contentLength(), totalBytesRead));
                return bytesRead;
            }
        };
    }
}
