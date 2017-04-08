package com.example.liangge.rxjavatest.common.constant;

/**
 * Created by guhongliang on 2017/3/27.
 */

public class Header {
    private String user_id;
    private String service_code;

    public Header(String user_id, String service_code) {
        this.user_id = user_id;
        this.service_code = service_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }
}
