package com.example.liangge.rxjavatest.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guhongliang on 2017/7/31.
 * 事件类
 */

public class MessageEvent implements Parcelable {
    private boolean change;
    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRead() {
        return read;
    }

    public void setRead(long read) {
        this.read = read;
    }

    private long read;

    public MessageEvent(long total, long read) {
        this.total = total;
        this.read = read;
    }

    public MessageEvent() {
    }

    public MessageEvent(boolean change) {
        this.change = change;
    }

    protected MessageEvent(Parcel in) {
        change = in.readByte() != 0;
    }

    public static final Creator<MessageEvent> CREATOR = new Creator<MessageEvent>() {
        @Override
        public MessageEvent createFromParcel(Parcel in) {
            return new MessageEvent(in);
        }

        @Override
        public MessageEvent[] newArray(int size) {
            return new MessageEvent[size];
        }
    };

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (change ? 1 : 0));
    }
}
