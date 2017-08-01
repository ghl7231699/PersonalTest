package com.example.liangge.rxjavatest.bean;

/**
 * Created by guhongliang on 2017/7/31.
 * 事件类
 */

public class MessageEvent {
    private boolean change;

    public MessageEvent() {
    }

    public MessageEvent(boolean change) {
        this.change = change;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }
}
