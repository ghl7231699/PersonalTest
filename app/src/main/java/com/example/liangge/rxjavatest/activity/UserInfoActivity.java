package com.example.liangge.rxjavatest.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.contract.UserInfoContract;
import com.example.liangge.rxjavatest.presenter.LoginPresenter;
import com.example.liangge.rxjavatest.utils.BeanTest;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.View {
    @BindView(R.id.user_info_text_content)
    TextView mUserInfoTextContent;
    private ProgressDialog mProgressDialog;
    private UserInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        new LoginPresenter(this);
        mPresenter.start();
    }

    @Override
    public void setPresenter(Object presenter) {
        this.mPresenter = (UserInfoContract.Presenter) presenter;
    }

    @Override
    public void showError(String s) {
        mUserInfoTextContent.setText(s);
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "加载中。。。。。", Toast.LENGTH_SHORT).show();
        mProgressDialog.setMessage("加载中。。。。");
        mProgressDialog.setTitle("提示");
        mProgressDialog.show();
//        mProgressDialog=ProgressDialog.show(UserInfoActivity.this,"提示","加载中。。。。。");
    }

    @Override
    public void disMissLoading() {
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showUserInfo(BeanTest model) {
        if (model != null) {
            mUserInfoTextContent.setText(new Gson().toJson(model));
        } else {
            Toast.makeText(this, "请求出错了，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String LoadUrl() {
        return "1000";
    }
}
