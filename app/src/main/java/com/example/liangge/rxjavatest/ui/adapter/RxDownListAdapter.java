package com.example.liangge.rxjavatest.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * Created by guhongliang on 2017/5/8.
 */

public class RxDownListAdapter extends BaseQuickAdapter<Fruit, BaseViewHolder> {
    TextView mRxDownLoadContent;
    TextView mRxDownLoadPer;
    ProgressBar mRxDownLoadPb;
    TextView mRxDownLoadLoading;
    TextView mRxDownLoadSize;
    Button mRxDownLoadBtn;
    private Context mContext;

    public RxDownListAdapter(Context context, List<Fruit> data) {
        super(R.layout.rx_down_list_item, data);
        this.mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.rx_down_list_item, null, false);
        mRxDownLoadContent = (TextView) view.findViewById(R.id.rx_down_load_content);
        mRxDownLoadPer = (TextView) view.findViewById(R.id.rx_down_load_per);
        mRxDownLoadLoading = (TextView) view.findViewById(R.id.rx_down_load_loading);
        mRxDownLoadSize = (TextView) view.findViewById(R.id.rx_down_load_size);
        mRxDownLoadPb = (ProgressBar) view.findViewById(R.id.rx_down_load_pb);
        mRxDownLoadBtn = (Button) view.findViewById(R.id.rx_down_load_btn);

        ButterKnife.bind((Activity) mContext);
    }

    @Override
    protected void convert(BaseViewHolder helper, Fruit item) {
        helper.setText(R.id.rx_down_load_content, item.getName());
        helper.setBackgroundRes(R.id.rx_down_load_item_img, item.getImageId());
        Glide.with(mContext).load(item.getImageId()).into((ImageView) helper.getView(R.id.rx_down_load_item_img));
        helper.addOnClickListener(R.id.rx_down_load_btn);
        helper.setOnClickListener(R.id.rx_down_load_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxDownload.getInstance(mContext)
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
        });
    }
}
