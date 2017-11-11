package com.example.liangge.rxjavatest.ndk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ndk.baseactivity.BaseNdkActivity;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

/**
 * Created by ghl11 on 2017/11/5.
 */

public class NdkActivity extends BaseNdkActivity {
    private TextView mTv;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mTv = findViewById(R.id.main_tv);
        showToast();

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

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
    }

    private void showToast() {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        View view = LayoutInflater.from(this).inflate(R.layout.stay_house_resource_item, null, false);
        toast.setView(view);
        toast.show();
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
}
