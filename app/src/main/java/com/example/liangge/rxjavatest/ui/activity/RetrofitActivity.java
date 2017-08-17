package com.example.liangge.rxjavatest.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.MessageEvent;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.RetrofitUtil;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.data.http.Api;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Ø
 * Created by guhongliang on 2017/8/16.
 */

public class RetrofitActivity extends BaseActivity {


    private static final String TAG = "RetrofitActivity";
    @BindView(R.id.retrofit_up_btn)
    Button up;
    Api api;
    @BindView(R.id.rx_down_load_item_img)
    ImageView mRxDownLoadItemImg;
    @BindView(R.id.rx_down_load_content)
    TextView mRxDownLoadContent;
    @BindView(R.id.rx_down_load_loading)
    TextView mRxDownLoadLoading;
    @BindView(R.id.rx_down_load_per)
    TextView mRxDownLoadPer;
    @BindView(R.id.rx_down_load_pb)
    ProgressBar mRxDownLoadPb;
    @BindView(R.id.rx_down_load_size)
    TextView mRxDownLoadSize;
    @BindView(R.id.rx_down_load_btn)
    Button mRxDownLoadBtn;
    private String[] mSplit;

    @Override
    public int getLayoutId() {
        return R.layout.retrofit_activity_layout;
    }

    @Override
    public void initView() {
        api = RetrofitUtil.retrofitInitialize(HttpUrls.DOWN_LOAD_UR_IP);
//        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
//        OkHttpClient client = mBuilder
//                .addNetworkInterceptor(new ProgressResponseBodyInterceptor())
//                .build();
//        api = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .baseUrl(HttpUrls.DOWN_LOAD_UR_IP)
//                .build()
//                .create(Api.class);
        EventBus.getDefault().register(this);//订阅
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.retrofit_up_btn, R.id.rx_down_load_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rx_down_load_btn:
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("reminder");
                dialog.setMessage("loading");
                dialog.create().show();
                downFile();
                break;
            case R.id.retrofit_up_btn:
                break;
        }
    }

    /**
     * 下载请求
     */
    private void downFile() {
        Call<ResponseBody> call = api.downLoad(HttpUrls.URL_DOWN_LOAD);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, "onResponse: " + response.body().contentLength());
                if (response.isSuccessful()) {
                    saveFileSdCard(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, ": " + t.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.toast("fail");
                    }
                });
            }
        });
    }

    /**
     * 保存文件
     *
     * @param body
     */
    private void saveFileSdCard(ResponseBody body) {
        String path = Environment.getExternalStorageDirectory() + File.separator;
        mSplit = HttpUrls.URL_DOWN_LOAD.split("/");
        File f = new File(path, mSplit[mSplit.length - 1]);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = body.byteStream();
            os = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buff = new byte[2048];
        int len;
        try {
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onRespose(MessageEvent event) {
        boolean change = event.isChange();
        if (!change) {
            mRxDownLoadContent.setText(mSplit[mSplit.length - 1]);
            mRxDownLoadPb.setVisibility(View.VISIBLE);
            mRxDownLoadLoading.setText("正在下载");
            int total = (int) event.getTotal();
            int totalM = (int) (event.getTotal() / (1024 * 1024));
            mRxDownLoadPb.setMax(total);
            int read = (int) event.getRead();
            int readM = (int) (event.getRead() / (1024 * 1024));
            int i = read / total * 100;
            mRxDownLoadPb.setProgress(read);
            mRxDownLoadSize.setText(readM + "M" + "/" + totalM + "M");
            mRxDownLoadPer.setText(i + "%");
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();
    }
}

