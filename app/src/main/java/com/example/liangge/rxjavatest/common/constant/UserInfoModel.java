package com.example.liangge.rxjavatest.common.constant;

/**
 * Created by guhongliang on 2017/4/6.
 */

public class UserInfoModel {
    private String name;
    private int age;
    private String address;

    public UserInfoModel(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
