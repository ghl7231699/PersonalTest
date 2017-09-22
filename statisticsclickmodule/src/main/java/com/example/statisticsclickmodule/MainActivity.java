package com.example.statisticsclickmodule;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.mylibrary.ActivityCollector;
import com.example.mylibrary.PermissionActivity;
import com.example.mylibrary.PermissionListener;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends BaseActivity implements ClickListener {
    private Button mButton, mBtn;
    private StringBuilder sb = new StringBuilder();
    private WebView mWebView;
    private MyHandler mHandler;

    static class MyHandler extends android.os.Handler {
        WeakReference<Activity> mWeak;

        public MyHandler(Activity activity) {
            mWeak = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        mButton = (Button) findViewById(R.id.btn_click);
        mBtn = (Button) findViewById(R.id.btn2);
        mWebView = (WebView) findViewById(R.id.web_view);
        mHandler = new MyHandler(this);
        mButton.setOnClickListener(this);
        mBtn.setOnClickListener(this);
        String activity = ClassUtils.getCurrentActivity(this);
        sb.append(activity);
        String deviceId = ClassUtils.getDeviceId(getApplicationContext());
        sb.append(deviceId);

        WebSettings settings = mWebView.getSettings();
        //设置与Js交互的权限
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //步骤1


        ClickAgent.init(this);
//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.super.setOnClick(view);
//                Toast.makeText(MainActivity.this, "点击了第二个", Toast.LENGTH_SHORT).show();
//                byInterface();
//            }
//        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                Toast.makeText(MainActivity.this, "点击了第一个", Toast.LENGTH_SHORT).show();
                super.setCustomClick(1, "搜索房源按钮");

                mWebView.loadUrl("file:///android_asset/javascript.html");

                // 复写WebViewClient类的shouldOverrideUrlLoading方法
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // 步骤2：根据协议的参数，判断是否是所需要的url
                        // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                        //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                        try {
                            URI uri = new URI(url);
                            // 如果url的协议 = 预先约定的 js 协议
                            // 就解析往下解析参数
                            System.out.print("js调用了Android的方法");
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                });
                break;
            case R.id.btn2:
//                byInterface();
//                Intent intent = new Intent(this, HouseDetailsActivity.class);
//                Intent intent = new Intent(this, BuildingDetailsActivity.class);
//                intent.putExtra("key_url", "file:///android_asset/javascript.html");
//                intent.putExtra("key_url", "file:///android_asset/detail.html");
//                intent.putExtra("key_url", "https://www.baidu.com");
//                startActivity(intent);

                PermissionActivity.onRequestPermissionResult(new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "权限通过", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String p :
                                deniedPermission) {
                            Toast.makeText(MainActivity.this, "权限被拒绝" + p, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                break;
            default:
                break;
        }
    }

    private void byInterface() {
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        mWebView.addJavascriptInterface(new AndroidToJs(), "test");//AndroidtoJS类对象映射到js的test对象
//        mWebView.loadUrl("file:///android_asset/javascript.html");
        mWebView.loadUrl("http://www.baidu.com/");
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
