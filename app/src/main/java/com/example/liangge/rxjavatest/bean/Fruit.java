package com.example.liangge.rxjavatest.bean;

/**
 * Created by liangge on 2017/4/3.
 */

public class Fruit {
    private String name;
    public int imageId;
    private int t;
    private int flag;
    public String url;
    private boolean top;

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public Fruit() {
    }

    public Fruit(int i) {
        imageId = i;
    }

    public Fruit(String s) {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public Fruit(String name, int imageId, int t, int flag, String url) {
        this.name = name;
        this.imageId = imageId;
        this.t = t;
        this.flag = flag;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
