package com.example.statisticsclickmodule;

import android.app.Activity;
import android.content.Intent;
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

import java.util.HashMap;

public class HouseDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_URL = "key_url";
    public static final String KEY_TITLE = "key_title";

    private static final int MSG_FINISHING = 0;
    private static final int MSG_GO_BACK = 1;
    private static final int MSG_GO_Forward = 2;
    private static final int MSG_GO_FORWARD_A = 3;

    private ProgressBar mProgressBar;
    private WebView mWebView;

    private String mUrl;
    private String mTitle;
    private Handler mHandler;

    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_house_details);
        initView();
        getIntentValue();
        initData();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        mWebView = (WebView) findViewById(R.id.mWebView);
    }

    private void getIntentValue() {
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
//                if (msg.what == MSG_FINISHING) {
//                    if (!isFinishing()) {
//                        finish();
//                    }
//                } else if (msg.what == MSG_GO_BACK) {
//                    if (mWebView != null && mWebView.canGoBack()) {
//                        mWebView.goBack();
//                    }
//                }

                switch (msg.what) {
                    case MSG_FINISHING:
                        if (!isFinishing()) {
                            finish();
                        }
                        break;
                    case MSG_GO_BACK:
                        boolean can = mWebView.canGoBack();
                        if (mWebView != null && can) {
                            mWebView.goBack();
                        }
                        break;
                    case MSG_GO_Forward:
                        String url = (String) msg.obj;
                        if (mWebView != null) {
                            mWebView.loadUrl(url);
                        }
                    case MSG_GO_FORWARD_A:
                        String s = (String) msg.obj;
                        Intent intent = new Intent(HouseDetailsActivity.this, BuildingDetailsActivity.class);
                        intent.putExtra(KEY_URL, s);
                        startActivity(intent);
//                        HouseDetailsActivity.this.finish();
//                        if (mWebView != null) {
//                            mWebView.loadUrl(s);
//                        }
                        break;
                    default:
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
        mWebView.addJavascriptInterface(new HouseDetailsActivity.JsToNative(), "AppHandler");
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onClick(View v) {

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

    private class JsToNative {

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
        public void goForward(String url) {
            if (mHandler != null) {
                Message msg = Message.obtain();
                msg.what = MSG_GO_Forward;
                msg.obj = url;
                mHandler.sendMessage(msg);
            }
        }

        @JavascriptInterface
        public void goActivity(String url) {
            if (mHandler != null) {
                Message msg = Message.obtain();
                msg.what = MSG_GO_FORWARD_A;
                msg.obj = url;
                mHandler.sendMessage(msg);
            }
        }

    }
}
