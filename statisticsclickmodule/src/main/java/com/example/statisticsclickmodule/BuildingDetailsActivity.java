package com.example.statisticsclickmodule;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by guhongliang on 2017/9/15.
 * 楼盘详情页
 */

public class BuildingDetailsActivity extends AppCompatActivity {
    public static final String KEY_URL = "key_url";
    public static final String KEY_TITLE = "key_title";
    private ProgressBar mProgressBar;
    private WebView mWebView;

    private static final int MSG_FINISHING = 0;
    private static final int MSG_GO_BACK = 1;
    private static final int MSG_GO_BUILDING_HOUSES_DETAIL = 5;

    private String mUrl;
    private String mTitle;
    private Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details);
        getIntentValue();
        initData();
    }

    private void getIntentValue() {
        mWebView = (WebView) findViewById(R.id.mWebView_bu);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar_bu);
        mUrl = getIntent().getStringExtra(KEY_URL);
        mTitle = getIntent().getStringExtra(KEY_TITLE);
        if (TextUtils.isEmpty(mUrl)) {
            if (!isFinishing()) {
                finish();
            }
        }
    }

    private void initData() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_FINISHING:
                        if (!isFinishing()) {
                            finish();
                        }
                        break;
                    case MSG_GO_BACK:
                        if (mWebView != null && mWebView.canGoBack()) {
                            mWebView.goBack();
                        }
                        break;
                    case MSG_GO_BUILDING_HOUSES_DETAIL:
                        String params = (String) msg.obj;
                        //跳转到楼盘房源列表页
                        //具体操作，以实际情况为准
                        //HouseMatchActivity ？楼盘下房源页面？
                        if (mWebView != null) {
                            mWebView.loadUrl(params);
                        }
                        break;
                }
            }
        };

        WebSettings websettings = mWebView.getSettings();
        websettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.addJavascriptInterface(new BuildingDetailsActivity.JsToNativeTo(), "AppHandler");
        mWebView.loadUrl(mUrl);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                if (!isFinishing()) {
                    finish();
                }
            }
        }
        return false;
    }

    private class JsToNativeTo {

        @JavascriptInterface
        public void closeH5() {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(MSG_FINISHING);
            }
        }

        @JavascriptInterface
        public void goBackH5() {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(MSG_GO_BACK);
            }
        }

        @JavascriptInterface
        public void goDetail(String url) {
            if (mHandler != null) {
                Message msg = Message.obtain();
                msg.what = MSG_GO_BUILDING_HOUSES_DETAIL;
                msg.obj = url;
                mHandler.sendMessage(msg);
            }
        }
    }
}
