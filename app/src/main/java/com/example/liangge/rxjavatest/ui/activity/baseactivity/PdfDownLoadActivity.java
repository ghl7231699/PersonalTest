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
    private static String url = "http://vpan.bj189.cn/xhrs/EBT2/NCI_EBT2.0_ZY_5.0.apk";
//    private static String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493288790631&di=b80e1abbeabfd2b4bab5552fec6ef157&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Flvpics%2Fh%3D800%2Fsign%3D9931b79f1dd5ad6eb5f969eab1ca39a3%2Fa8773912b31bb051b3333f73307adab44aede052.jpg";

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
                OkHttpUtils.get(url)//
                        .tag(this)//
                        .execute(new DownloadFileCallBack(Environment.getExternalStorageDirectory() +
                                "/temp/glide", "刘东.apk"));//保存到sd卡
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
