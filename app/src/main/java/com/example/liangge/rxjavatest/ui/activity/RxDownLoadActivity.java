package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * Created by guhongliang on 2017/5/5.
 */

public class RxDownLoadActivity extends BaseActivity {
    @BindView(R.id.rx_down_load_btn)
    Button mRxDownLoadBtn;
    @BindView(R.id.rx_down_load_loading)
    TextView mRxDownLoadLoading;
    @BindView(R.id.rx_down_load_img)
    ImageView mRxDownLoadImg;
    @BindView(R.id.rx_down_load_pb)
    ProgressBar mRxDownLoadPb;
    @BindView(R.id.rx_down_load_size)
    TextView mRxDownLoadSize;
    @BindView(R.id.rx_down_load_per)
    TextView mRxDownLoadPer;
    private boolean f = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_down_load_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @OnClick(R.id.rx_down_load_btn)
    public void onViewClicked() {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
