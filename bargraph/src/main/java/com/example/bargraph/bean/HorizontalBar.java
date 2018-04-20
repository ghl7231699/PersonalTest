package com.example.bargraph.bean;

/**
 * Created by guhongliang on 2018/3/16.
 */

public class HorizontalBar {
    private String userName;//用户名称
    private int activityNo;//激活量
    private int increasedNo;//新增量
    private int claimNo;//认领量

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(int activityNo) {
        this.activityNo = activityNo;
    }

    public int getIncreasedNo() {
        return increasedNo;
    }

    public void setIncreasedNo(int increasedNo) {
        this.increasedNo = increasedNo;
    }

    public int getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(int claimNo) {
        this.claimNo = claimNo;
    }

    public String getTextContent() {
        return userName;
    }

    public void setTextContent(String textContent) {
        this.userName = textContent;
    }

    public int getTextItem() {
        return activityNo;
    }

    public void setTextItem(int textItem) {
        this.activityNo = textItem;
    }
}
