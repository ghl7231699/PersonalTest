package com.example.liangge.rxjavatest.contract;

import com.example.liangge.rxjavatest.presenter.BasePresenter;
import com.example.liangge.rxjavatest.utils.BeanTest;

/**
 * Created by guhongliang on 2017/4/6.
 */

public interface UserInfoContract {
    interface View extends BaseView {
        void showLoading();//展示加载框

        void disMissLoading();//取消加载框展示

        void showUserInfo(BeanTest model);//将网络请求得到的用户信息回调

        String LoadUrl();
    }

    interface Presenter extends BasePresenter {
        void LoadUserInfo();
    }
}
