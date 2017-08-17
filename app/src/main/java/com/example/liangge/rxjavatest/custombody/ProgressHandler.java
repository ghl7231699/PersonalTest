package com.example.liangge.rxjavatest.custombody;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.liangge.rxjavatest.bean.MessageEvent;

/**
 * Created by guhongliang on 2017/8/17.
 */

public abstract class ProgressHandler {
    protected abstract void sendMessage(MessageEvent event);

    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {
        private ProgressHandler mHandler;

        public ResponseHandler(ProgressHandler handler, Looper looper) {
            super(looper);
            mHandler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            mHandler.handleMessage(msg);
        }
    }
}
