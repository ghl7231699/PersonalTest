package com.example.liangge.rxjavatest.ui.activity;

import android.app.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.di.component.DaggerUserComponent;
import com.example.liangge.rxjavatest.di.modules.UserModules;
import com.example.liangge.rxjavatest.presenter.LoginPresenter;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.google.gson.Gson;

import java.io.File;

import butterknife.BindView;

public class UserInfoActivity extends BaseActivity<LoginPresenter> implements UserInfoContract.View {
    @BindView(R.id.user_info_text_content)
    TextView mUserInfoTextContent;
    private ProgressDialog mProgressDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        mProgressDialog = new ProgressDialog(this);
//        mPresenter.start();
    }


    @Override
    public void initData() {
        mPresenter.LoadUserInfo();
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {
        DaggerUserComponent.builder().appComponent(appComponent)
                .userModules(new UserModules(this))
                .build()
                .inject(this);
    }

    @Override
    public void showError(String s) {
        mUserInfoTextContent.setText(s);
    }

    @Override
    public void showLoading() {
        mProgressDialog.setMessage("加载中。。。。");
        mProgressDialog.setTitle("提示");
        mProgressDialog.show();
    }

    @Override
    public void loadDetailContent(File file, String s) {

    }

    @Override
    public void disMissLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showUserInfo(Object model) {
        if (model != null) {
            mUserInfoTextContent.setText(new Gson().toJson(model));
        } else {
            Toast.makeText(this, "请求出错了，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);

    }
}
