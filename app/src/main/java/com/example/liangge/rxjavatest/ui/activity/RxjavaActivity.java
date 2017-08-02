package com.example.liangge.rxjavatest.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.di.component.DaggerUserComponent;
import com.example.liangge.rxjavatest.di.modules.UserModules;
import com.example.liangge.rxjavatest.presenter.RxjavaPresenter;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by guhongliang on 2017/8/2.
 */

public class RxjavaActivity extends BaseActivity<RxjavaPresenter> implements UserInfoContract.RxView {
    @BindView(R.id.rx_java_content)
    TextView mContent;

    @Override
    public int getLayoutId() {
        return R.layout.rxjava_activity_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .userModules(new UserModules(this))
                .build()
                .injects(this);
    }

    @Override
    public void showError(String s) {
        ;

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void loadDetailContent(File file, String s) {

    }

    @Override
    public void disMissLoading() {

    }

    @Override
    public void showUserInfo(Object model, int type) {


//            observable.subscribe(new Consumer<String>() {
//                @Override
//                public void accept(@NonNull String s) throws Exception {
//                    mContent.append(s);
//                }
//            });
        switch (type) {

            case 1:
                Observable<String> s = (Observable<String>) model;
                create(s);
                break;
            case 2:
                Observable<Integer> i = (Observable<Integer>) model;
                map(i);
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private void create(Observable<String> observable) {
        observable.subscribe(new Observer<String>() {
            private int mInt;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mContent.append("onSubscribe " + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                mContent.append(s);
                mContent.append("onNext " + mInt + mDisposable.isDisposed() + "\n");
                mInt++;
                if (mInt == 2) {
                    mDisposable.dispose();
                    mContent.append("onNext " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void map(Observable<Integer> observable) {
        observable.map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "this is" + integer + "\n";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mContent.append(s);
            }
        });
    }

    @OnClick({R.id.rx_java_create, R.id.rx_java_map, R.id.rx_java_concat, R.id.rx_java_flatMap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rx_java_create:
                mPresenter.create();
                break;
            case R.id.rx_java_map:
                mPresenter.map();
                break;
            case R.id.rx_java_concat:
                break;
            case R.id.rx_java_flatMap:
                break;
        }
    }
}

