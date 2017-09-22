package com.example.bargraph.watcher;

/**
 * Created by guhongliang on 2017/9/22.
 */

public class Woman implements Watcher {
    private TransmitListener mListener;

    public Woman(TransmitListener listener) {
        mListener = listener;
    }

    @Override
    public void update(Object object) {
        mListener.transmit(object);
    }
}
