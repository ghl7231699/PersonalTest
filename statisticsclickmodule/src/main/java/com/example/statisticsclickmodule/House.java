package com.example.statisticsclickmodule;

import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by guhongliang on 2017/9/12.
 */

public class House implements IHouse {
    private String name;
    private String money;
    private boolean sign;

    public House(String name, String money) {
        this.name = name;
        this.money = money;
        ClassUtils.getCurrentActivity(this);
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    @Override
    public void getHouseInfo() {
        Log.e(TAG, "getHouseInfo:房源信息" + name);
    }

    @Override
    public void signContract() {
        Log.e(TAG, "signContract: 签署合约 ");
    }

    @Override
    public void payFees() {
        if (sign) {
            Log.e(TAG, "payFees: 签约成功，支付租金");
        } else {
            Log.e(TAG, "payFees: 签约失败");
        }
    }
}
