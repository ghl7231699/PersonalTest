package com.example.liangge.rxjavatest.common.constant;

import java.util.List;

/**
 * Created by guhongliang on 2018/3/6.
 */

public class HomePageBean {
    private String userName;
    private String content;
    private String date;
    private int imageId;
    private int position;
    private List<HomePageBean> home;

    public List<HomePageBean> getHome() {
        return home;
    }

    public void setHome(List<HomePageBean> home) {
        this.home = home;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HomePageBean() {
    }

    public HomePageBean(String content, int imageId) {
        this.content = content;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
