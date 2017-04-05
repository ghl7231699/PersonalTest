package com.example.liangge.rxjavatest.constant;

/**
 * Created by liangge on 2017/3/26.
 */

public class BaseResult {
    private User data;
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public User getUser() {
        return data;
    }

    public void setUser(User user) {
        this.data = user;
    }
}
