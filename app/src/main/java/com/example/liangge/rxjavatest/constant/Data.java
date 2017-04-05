package com.example.liangge.rxjavatest.constant;

/**
 * Created by guhongliang on 2017/3/27.
 */

public class Data {
    private String password;
    private String user_name;

    public Data(String password, String user_name) {
        this.password = password;
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
