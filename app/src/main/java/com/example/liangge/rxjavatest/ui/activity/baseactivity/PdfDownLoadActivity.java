package com.example.liangge.rxjavatest.ui.activity.baseactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ui.activity.PdfActivity;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class PdfDownLoadActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.fileDownload)
    Button mFileDownload;
    @BindView(R.id.downloadSize)
    TextView mDownloadSize;
    @BindView(R.id.netSpeed)
    TextView mNetSpeed;
    @BindView(R.id.tvProgress)
    TextView mTvProgress;
    @BindView(R.id.show_pdf)
    Button mShowPdf;
    String pdfUrl = "http://partners.adobe.com/public/developer/en/xml/AdobeXMLFormsSamples.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_down_load);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fileDownload, R.id.show_pdf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fileDownload:
                OkHttpUtils.get(pdfUrl)//
                        .tag(this)//
                        .execute(new DownloadFileCallBack(Environment.getExternalStorageDirectory() +
                                "/temp", "qcl.pdf"));//保存到sd卡
                break;
            case R.id.show_pdf:
                startActivity(new Intent(this, PdfActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private class DownloadFileCallBack extends FileCallback {


        @Override
        public void onBefore(BaseRequest request) {
            mFileDownload.setText("正在下载中");
        }

        public DownloadFileCallBack(@NonNull String destFileDir, @NonNull String destFileName) {
            super(destFileDir, destFileName);
        }

        @Override
        public void onResponse(boolean isFromCache, File file, Request request, @Nullable Response response) {
            mFileDownload.setText("下载完成");
            mShowPdf.setVisibility(View.VISIBLE);
        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            String downloadLength = Formatter.formatFileSize(getApplicationContext(),
                    currentSize);
            String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
            mDownloadSize.setText(downloadLength + "/" + totalLength);
            String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
            mNetSpeed.setText(netSpeed + "/S");
            mTvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            mFileDownload.setText("下载出错");
        }
    }
}
