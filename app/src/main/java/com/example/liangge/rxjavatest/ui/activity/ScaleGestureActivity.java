package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.widget.ZoomView;

/**
 * 类描述：图片预览
 * <p>
 * 支持手势放大、缩小
 * </p>
 * 创建人：ghl
 * 创建时间：2018/9/26
 *
 * @version v1.0
 */

public class ScaleGestureActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_PATH = "KEY_URL_PATH";
    private ZoomView mImageView;
    //    private ZoomImageView mImageView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_gesture);
        getIntentValue();
        initView();
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        path = intent.getStringExtra(URL_PATH);
    }

    private void initView() {
        mImageView = (ZoomView) findViewById(R.id.scal_gesture_iv);
//        mImageView = (ZoomImageView) findViewById(R.id.scal_gesture_iv);
//        mImageView.setFilePath(this, path);
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/tmp.jpg");
//        mImageView.setImageURI(Uri.parse(path));
        mImageView.setImageBitmap(bitmap);
//        mImageView.setImageURI(Uri.parse("/storage/emulated/0/tmp.jpg"));
    }

    @Override
    public void onClick(View v) {

    }
}
