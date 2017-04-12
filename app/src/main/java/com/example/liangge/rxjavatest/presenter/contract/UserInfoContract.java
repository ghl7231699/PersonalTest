package com.example.liangge.rxjavatest.presenter.contract;

import com.example.liangge.rxjavatest.bean.BeanTest;

/**
 * Created by guhongliang on 2017/4/6.
 */

public interface UserInfoContract {
    interface View extends BaseView {
        void showLoading();//展示加载框

        void disMissLoading();//取消加载框展示

        void showUserInfo(Object model);//将网络请求得到的用户信息回调
    }
//
//    interface Presenter extends BasePresenter {
//        void LoadUserInfo();
//    }
}
