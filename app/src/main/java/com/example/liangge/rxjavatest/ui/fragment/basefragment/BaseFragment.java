package com.example.liangge.rxjavatest.ui.fragment.basefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.presenter.BasePresenter;

import javax.inject.Inject;

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
    private View rootView;

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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mApp = (App) getActivity().getApplication();
        setUpComponent(mApp.getAppComponent());
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
