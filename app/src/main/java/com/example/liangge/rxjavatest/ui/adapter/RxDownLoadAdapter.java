package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liangge.rxjavatest.App;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static android.content.ContentValues.TAG;

/**
 * Created by guhongliang on 2017/5/8.
 */

public class RxDownLoadAdapter extends RecyclerView.Adapter<RxDownLoadAdapter.ViewHolder> {
    List<Fruit> mFruits;

    private Context mContext;
    private RxDownload mDownload;
    Disposable disposable;

    public RxDownLoadAdapter(List<Fruit> fruits, Context context) {
        mFruits = fruits;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rx_down_list_item, parent, false));
        viewHolder.mRxDownLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = mFruits.get(position);
                String url = fruit.getUrl();
                get(viewHolder, url);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruits.get(position);
        holder.mRxDownLoadContent.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.mRxDownLoadItemImg);
    }

    @Override
    public int getItemCount() {
        return mFruits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rx_down_load_item_img)
        ImageView mRxDownLoadItemImg;
        @BindView(R.id.rx_down_load_content)
        TextView mRxDownLoadContent;
        @BindView(R.id.rx_down_load_per)
        TextView mRxDownLoadPer;
        @BindView(R.id.rx_down_load_pb)
        ProgressBar mRxDownLoadPb;
        @BindView(R.id.rx_down_load_loading)
        TextView mRxDownLoadLoading;
        @BindView(R.id.rx_down_load_size)
        TextView mRxDownLoadSize;
        @BindView(R.id.rx_down_load_btn)
        Button mRxDownLoadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void get(final ViewHolder holder, String s) {
        mDownload = RxDownload.getInstance(mContext)
//                .maxThread(10)
//                .maxRetryCount(10)
                .defaultSavePath(App.getLocalDataPath());
        String[] split = s.split("/");
        String name = null;
        for (int i = 0; i < split.length; i++) {
            name = split[split.length - 1];
        }
        Log.d(TAG, "get: " + mDownload.hashCode());
        if (holder.mRxDownLoadBtn.getText().equals("下载")) {
            set(holder, s);
//            mDownload.download(s, name, null)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<DownloadStatus>() {
//                        @Override
//                        public void accept(@NonNull DownloadStatus downloadStatus) throws Exception {
//                            String formatDownloadSize = downloadStatus.getFormatDownloadSize();
//                            String formatTotalSize = downloadStatus.getFormatTotalSize();
//                            int size = (int) downloadStatus.getDownloadSize();
//                            int totalSize = (int) downloadStatus.getTotalSize();
//                            String percent = downloadStatus.getPercent();
//                            holder.mRxDownLoadLoading.setText("正在下载");
//                            holder.mRxDownLoadBtn.setText("暂停");
//                            holder.mRxDownLoadPb.setVisibility(View.VISIBLE);
//                            holder.mRxDownLoadPb.setMax(totalSize);
//                            holder.mRxDownLoadPb.setProgress(size);
//                            holder.mRxDownLoadSize.setText(formatDownloadSize + "/" + formatTotalSize);
//                            holder.mRxDownLoadPer.setText(percent);
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(@NonNull Throwable throwable) throws Exception {
//                            //download failed
//                            ToastUtils.toast("下载失败");
//                        }
//                    }, new Action() {
//                        @Override
//                        public void run() throws Exception {
//                            //downLoad success
//                            holder.mRxDownLoadLoading.setText("下载完成");
//                            holder.mRxDownLoadBtn.setText("完成");
//                        }
//                    });
        } else if (holder.mRxDownLoadBtn.getText().equals("暂停")) {
            //...//取消订阅, 即可暂停下载, 若服务端不支持断点续传,下一次下载会重新下载,反之会继续下载
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            holder.mRxDownLoadBtn.setText("继续");
        } else if (holder.mRxDownLoadBtn.getText().equals("继续")) {
            set(holder, s);
        } else if (holder.mRxDownLoadBtn.getText().equals("完成")) {
            ToastUtils.toast("已经下载完成");
        }

    }

    private void set(final ViewHolder holder, String s) {
        mDownload.download(s).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadStatus>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(DownloadStatus downloadStatus) {
                        String formatDownloadSize = downloadStatus.getFormatDownloadSize();
                        String formatTotalSize = downloadStatus.getFormatTotalSize();
                        int size = (int) downloadStatus.getDownloadSize();
                        int totalSize = (int) downloadStatus.getTotalSize();
                        String percent = downloadStatus.getPercent();
                        holder.mRxDownLoadLoading.setText("正在下载");
                        holder.mRxDownLoadBtn.setText("暂停");
                        holder.mRxDownLoadPb.setVisibility(View.VISIBLE);
                        holder.mRxDownLoadPb.setMax(totalSize);
                        holder.mRxDownLoadPb.setProgress(size);
                        holder.mRxDownLoadSize.setText(formatDownloadSize + "/" + formatTotalSize);
                        holder.mRxDownLoadPer.setText(percent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.toast("下载失败");
                    }

                    @Override
                    public void onComplete() {
                        holder.mRxDownLoadLoading.setText("下载完成");
                        holder.mRxDownLoadBtn.setText("完成");
                    }
                });
    }
}
