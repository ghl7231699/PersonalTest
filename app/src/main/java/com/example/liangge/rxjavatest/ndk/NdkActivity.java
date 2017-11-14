package com.example.liangge.rxjavatest.ndk;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ndk.baseactivity.BaseNdkActivity;

import java.io.File;

/**
 * Created by ghl11 on 2017/11/5.
 */

public class NdkActivity extends BaseNdkActivity implements View.OnClickListener {
    private TextView mTv;
    private Button fix, calutor;

    private String mDirCache;

    static {
        System.loadLibrary("native-lib");
        init();
    }

    public String name = "沁园春";

    private int[] source = {1, 0, 9, 2, 4, 6};

    public String getName() {
        return "长征";
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initView() {
        mTv = findViewById(R.id.ndk_tv);
        fix = findViewById(R.id.ndk_fix);
        calutor = findViewById(R.id.ndk_calutor);

        fix.setOnClickListener(this);
        calutor.setOnClickListener(this);
    }

    @Override
    public void initData() {
        showToast();
    }

    /**
     * 自定义toast
     */
    private void showToast() {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        View view = LayoutInflater.from(this).inflate(R.layout.stay_house_resource_item, null, false);
        toast.setView(view);
        toast.show();
    }

    /**
     * 加载ndk方法
     */
    private void loadNdkMethod() {
        //        mTv.append(updateNameFromC() + "\n");
//
//        mTv.append(getMethod() + "\n");
//
//        getArray(source);
//        for (int i = 0; i < source.length; i++) {
//            mTv.append("this is " + source[i] + "\n");
//        }

//        try {
//            exception();
//        } catch (Exception e) {
//            mTv.append(e.getMessage());
//        }
//
//        for (int i = 0; i < 10; i++) {
//            cache();
//        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String updateNameFromC();//属性的访问

    public native void getArray(int[] arrays);//获取数组

    public native String getMethod();//获取方法

    //引用解决的问题是通知JVM回收JNI对象
    public native void getLocalReference();

    //异常的处理  c++的异常  java层面是无法try catch的,如果jni层面出现了异常，那么java的代码调用中止
    public native void exception();

    //缓存策略
    public native void cache();

    //动态库加载的时候初始化全局变量
    public native static void init();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ndk_calutor:
                Calutor calutor = new Calutor();
                mTv.setText(calutor.calutor());
                break;
            case R.id.ndk_fix:
                //创建缓存目录
                getmDirCache();
                if (mDirCache == null) {
                    return;
                }
                File file = new File(mDirCache);
                if (!file.exists()) {
                    file.mkdir();
                }
                AndFixPatchManager.addPatch(getPatchName());
                break;
        }
    }

    /**
     * 获取patch文件
     *
     * @return
     */
    private String getPatchName() {
        return mDirCache.concat("My").concat(".apatch");
    }

    /**
     * 判断sdCard是否可用
     *
     * @return
     */
    private String getmDirCache() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mDirCache = getExternalCacheDir().getAbsolutePath() + "/apatch/";
        }
        return mDirCache;
    }
}
