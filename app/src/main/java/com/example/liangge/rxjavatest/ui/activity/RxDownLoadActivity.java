package com.example.liangge.rxjavatest.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.di.component.DaggerUserComponent;
import com.example.liangge.rxjavatest.di.modules.UserModules;
import com.example.liangge.rxjavatest.presenter.RxDownLoadPresenter;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.adapter.RxDownLoadAdapter;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * Created by guhongliang on 2017/5/5.
 */

public class RxDownLoadActivity extends BaseActivity<RxDownLoadPresenter> implements UserInfoContract.View {
    TextView mRxDownLoadContent;
    TextView mRxDownLoadPer;
    ProgressBar mRxDownLoadPb;
    TextView mRxDownLoadLoading;
    TextView mRxDownLoadSize;
    Button mRxDownLoadBtn;
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_down_load_layout;
    }

    @Override
    public void initView() {
        View view = LayoutInflater.from(this).inflate(R.layout.rx_down_list_item, null, false);
        mRxDownLoadContent = (TextView) view.findViewById(R.id.rx_down_load_content);
        mRxDownLoadPer = (TextView) view.findViewById(R.id.rx_down_load_per);
        mRxDownLoadLoading = (TextView) view.findViewById(R.id.rx_down_load_loading);
        mRxDownLoadSize = (TextView) view.findViewById(R.id.rx_down_load_size);
        mRxDownLoadPb = (ProgressBar) view.findViewById(R.id.rx_down_load_pb);
        mRxDownLoadBtn = (Button) view.findViewById(R.id.rx_down_load_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.rx_down_load_recycler_view);
    }

    @Override
    public void initData() {
        mPresenter.showDownList();
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {
        DaggerUserComponent.builder().appComponent(appComponent).userModules(new UserModules(this))
                .build()
                .injects(this);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onRequestPermissonSuccess() {

    }

    @Override
    public void onRequestPermissonError() {

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
    public void showUserInfo(Object model) {
        List<Fruit> list = (List<Fruit>) model;
        if (list != null) {
//            RxDownListAdapter adapter = new RxDownListAdapter(this, list);
            RxDownLoadAdapter adapter = new RxDownLoadAdapter(list, this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private void get() {

        RxDownload.getInstance(this)
                .download(HttpUrls.URL_DOWN_LOAD, "展业区.apk", App.getLocalDataPath())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(@NonNull DownloadStatus downloadStatus) throws Exception {
                        String formatDownloadSize = downloadStatus.getFormatDownloadSize();
                        String formatTotalSize = downloadStatus.getFormatTotalSize();
                        int size = (int) downloadStatus.getDownloadSize();
                        int totalSize = (int) downloadStatus.getTotalSize();
                        String percent = downloadStatus.getPercent();
                        mRxDownLoadLoading.setText("正在下载");
                        mRxDownLoadBtn.setText("暂停");
                        mRxDownLoadPb.setVisibility(View.VISIBLE);
                        mRxDownLoadPb.setMax(totalSize);
                        mRxDownLoadPb.setProgress(size);
                        mRxDownLoadSize.setText(formatDownloadSize + "/" + formatTotalSize);
                        mRxDownLoadPer.setText(percent);
                        if (formatDownloadSize.equals(formatTotalSize)) {
                            mRxDownLoadLoading.setText("下载完成");
                            mRxDownLoadBtn.setText("完成");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtils.toast("下载失败");
                    }
                });
    }
}
