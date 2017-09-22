package com.example.bargraph.watcher;

/**
 * Created by guhongliang on 2017/9/22.
 * 具体的观察者
 */

public class Man implements Watcher {
    private TransmitListener mListener;

    public Man(TransmitListener listener) {
        mListener = listener;
    }

    @Override
    public void update(Object object) {
        mListener.transmit(object);
    }
}
