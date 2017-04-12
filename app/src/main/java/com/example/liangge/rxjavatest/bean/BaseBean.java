package com.example.liangge.rxjavatest.bean;

import java.io.Serializable;

/**
 * Created by liangge on 2017/4/11.
 */

public class BaseBean<T> implements Serializable {
    private Header header;
    private T data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
