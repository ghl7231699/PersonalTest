package com.example.liangge.rxjavatest.presenter.contract;

import java.io.File;

/**
 * Created by guhongliang on 2017/4/6.
 */

public interface UserInfoContract {
    interface View extends BaseView {
        void showLoading();//展示加载框

        void loadDetailContent(File file, String s);//加载详情页面的源文件

        void disMissLoading();//取消加载框展示

        void showUserInfo(Object model);//将网络请求得到的用户信息回调
    }

    //
//    interface Presenter extends BasePresenter {
//        void LoadUserInfo();
//    }
    interface RxView extends BaseView {

        void showLoading();//展示加载框

        void loadDetailContent(File file, String s);//加载详情页面的源文件

        void disMissLoading();//取消加载框展示

        void showUserInfo(Object model, int type);//将网络请求得到的用户信息回调,根据type类型处理
    }

}
