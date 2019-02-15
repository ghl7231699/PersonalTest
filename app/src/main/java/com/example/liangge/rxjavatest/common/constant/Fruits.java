package com.example.liangge.rxjavatest.common.constant;

/**
 * Created by liangge on 2017/4/3.
 */

public class Fruits {
    private String name;
    private int imageId;
    private int height;

    public Fruits(String name, int imageId, int height) {
        this.name = name;
        this.imageId = imageId;
        this.height = height;
    }

    public Fruits(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
