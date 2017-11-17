package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.content.res.Resources;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.manager.PluginManager;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by guhongliang on 2017/8/26.
 * 代理activity
 */

public class ProxyActivity extends BaseActivity {
    private String className;//需要跳转的activity类名
//    AliPayInterface mAliPayInterface;

    @Override
    public int getLayoutId() {
        return R.layout.proxy_activity_layout;
    }

    @Override
    public void initView() {
        className = getIntent().getStringExtra("key");
        try {
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            /**
             * 定义标准
             *
             *
             *
             * 传递生命周期
             */
//            mAliPayInterface = (AliPayInterface) instance;
//            mAliPayInterface.attach(this);
//            //可以传递信息
//            Bundle bundle = new Bundle();
//            mAliPayInterface.onCreate(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAliPayInterface.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mAliPayInterface.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAliPayInterface.onStop();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAliPayInterface.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mAliPayInterface.onDestroy();
//    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    /**
     * class 文件  资源文件
     *
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public void startActivity(Intent intent) {
        String className = getIntent().getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("key", className);
        super.startActivity(intent1);
    }
}
