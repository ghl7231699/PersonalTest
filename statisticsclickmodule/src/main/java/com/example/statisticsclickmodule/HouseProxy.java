package com.example.statisticsclickmodule;

/**
 * 类名称：HouseProxy
 * 类描述：房租代理
 * 创建人：ghl
 * 创建时间：2017/9/12 上午11:42
 * 修改人：ghl
 * 修改时间：2017/9/12 上午11:42
 *
 * @version v1.0
 */

public class HouseProxy implements IHouse {
    private IHouse mHouse;

    public void presenterTo(IHouse house) {
        mHouse = house;
    }

    @Override
    public void getHouseInfo() {
        mHouse.getHouseInfo();
    }

    @Override
    public void signContract() {
        mHouse.signContract();
    }

    @Override
    public void payFees() {
        mHouse.payFees();
    }
}
