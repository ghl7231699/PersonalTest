package com.example.liangge.rxjavatest.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.MessageEvent;
import com.example.liangge.rxjavatest.common.config.SslContextFactory;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.CommonUtils;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.view.PercentView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by guhongliang on 2017/8/9.
 * okHttp使用
 */

public class OkHttpActivity extends BaseActivity {
    private static final String TAG = "OkHttpActivity";
    @BindView(R.id.ok_http_get)
    Button mOkHttpGet;
    @BindView(R.id.ok_http_post)
    Button mOkHttpPost;
    @BindView(R.id.ok_http_tv)
    TextView mOkHttpTv;
    @BindView(R.id.ok_http_iv)
    ImageView mOkHttpIv;
    @BindView(R.id.ok_http_tv_total)
    TextView mPercentView;
    private byte[] mBytes;
    private OkHttpClient okHttpClient;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ProgressDialog pd;

    @Override
    public int getLayoutId() {
        return R.layout.ok_http_activity_layout;
    }

    @Override
    public void initView() {
        pd = new ProgressDialog(this);
    }

    @Override
    public void initData() {
        okHttpClient = SslContextFactory.getOkHttpClient();
        EventBus.getDefault().register(this);//订阅
    }

    /**
     * get方法
     */
    private void get() {
        //2 构造Request
        Request request = new Request.Builder().get().url("").build();
        //3 将request封装成call
        Call call = okHttpClient.newCall(request);
        //4 执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.toast(e.getMessage());
                Log.e(TAG, "onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String s = response.body().string();
                    //onResponse 方法不能直接操作 UI 线程，利用 runOnUiThread 操作 ui
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOkHttpTv.setText(s);
                        }
                    });
                }
            }
        });
    }

    /**
     * post方法
     */
    private void post() {
        //构造Request
        //构造RequestBody
        //1 普通表单
        RequestBody body = new FormBody.Builder()
                .add("键", "值")
                .add("键", "值")
                .build();
        //2 表单为json
        MediaType type = MediaType.parse("application/json;charset=utf-8");
        RequestBody body1 = RequestBody.create(type, new Gson().toJson(CommonUtils.getUserParam()));
        //3 multipart/form-data 数据里有文件
        File file = new File("");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        final Request request = new Request.Builder()
                .post(body1)
                .url(HttpUrls.URL_LOGIN_IP + "ju321/isb/isb-ucm-adapter-in/agentDetail")
                .build();
        //3 将request封装成call
        Call call = okHttpClient.newCall(request);
        //4 执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String s = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOkHttpTv.setText(s);
                        }
                    });
                }
            }
        });
    }

    /**
     * 上传文件
     */
    private void postFile() {
        File file = new File("");
        if (!file.exists()) {
            ToastUtils.toast("File is not exists");
            return;
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request request = new Request.Builder()
                .post(body)
                .url("")
                .build();
        executeRequest(request);
    }

    private void executeRequest(final Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String s = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOkHttpTv.setText(s);
                        }
                    });
                }
            }
        });
    }

    /**
     * 下载文件
     */
    private void downLoadImage() {
        final Request request = new Request.Builder()
                .get()
                .url(HttpUrls.URL_8)
                .build();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        Log.e(TAG, "onFailure");
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mOkHttpTv.setText(e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final InputStream is = response.body().byteStream();
                            //将图片存储在SD卡，读取字节流
                            String[] split = HttpUrls.URL_8.split("/");
                            File file = new File(Environment.getExternalStorageDirectory(), split[split.length - 1]);
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file);
                                int len;
                                byte[] buff = new byte[1024];
                                while ((len = is.read(buff)) != -1) {
                                    fos.write(buff, 0, len);
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOkHttpIv.setImageBitmap(bitmap);
                                    }
                                });

                                Log.e(TAG, "downLoad success");
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (fos != null) {
                                    fos.flush();
                                    fos.close();
                                }
                                if (is != null) {
                                    is.close();
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onRespose(MessageEvent event) {
        mOkHttpTv.setText("" + event.getRead());
        mPercentView.setText("" + event.getTotal());
//        pd = new ProgressDialog(this);
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pd.setTitle("提示");
//        pd.setMessage("下载中");
//        pd.setProgress((int) event.getRead());
//        pd.setMax((int) event.getTotal());
//        pd.show();
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.ok_http_get, R.id.ok_http_post, R.id.ok_http_post_file, R.id.ok_http_down_image, R.id.ok_http_post_json})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ok_http_get:
                get();
                break;
            case R.id.ok_http_post:
                post();
                break;
            case R.id.ok_http_post_file:
                postFile();
                break;
            case R.id.ok_http_post_json:
                break;
            case R.id.ok_http_down_image:
                downLoadImage();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();
    }
}
