package com.example.liangge.rxjavatest.ui.fragment.basefragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liangge on 2017/4/9.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private Unbinder mUnbinder;
    private App mApp;
    //    @Inject
//    T mPresenter;
    protected View rootView;
    protected Activity mActivity;

    //获取布局Id
    public abstract int getLayoutId();

    //初始化view
    public abstract void initView();

    //初始化监听事件
    public abstract void initListener();

    //初始化数据
    public abstract void initData();

    //处理点击事件
    public abstract void processClick(View v);

    //初始化Component
    public abstract void setUpComponent(AppComponent appComponent);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mApp = (App) getActivity().getApplication();
        setUpComponent(mApp.getAppComponent());
        initView();
        initData();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
