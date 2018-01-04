package com.example.liangge.rxjavatest.ndk;

import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.ndk.baseactivity.BaseNdkActivity;
import com.example.liangge.rxjavatest.thinker.ThinkerManger;
import com.example.liangge.rxjavatest.ui.view.UnreadMessageView;

import java.io.File;

/**
 * Created by ghl11 on 2017/11/5.
 */

public class NdkActivity extends BaseNdkActivity implements View.OnClickListener {
    private TextView mTv;
    private Button fix, calutor;

    private String mDirCache;

    private UnreadMessageView mMessageView;

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
        mTv = (TextView) findViewById(R.id.ndk_tv);
        fix = (Button) findViewById(R.id.ndk_fix);
        mMessageView = (UnreadMessageView) findViewById(R.id.ndk_custom);
        calutor = (Button) findViewById(R.id.ndk_calutor);

        mMessageView.setContent("未读消息");
        mMessageView.setNum("8");
        mMessageView.setColor(Color.BLUE);

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
        View view = LayoutInflater.from(this).inflate(R.layout.loadding, null, false);
        TextView content = view.findViewById(R.id.load_content);
        content.setText("朱日和");
        ToastUtils.showWidgetView(view);
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
//                Calutor calutor = new Calutor();
//                try {
//                    calutor.calutor();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                mTv.setText("替换后");
//                }


                break;
            case R.id.ndk_fix:
//                //创建缓存目录
//                getmDirCache();
//                if (mDirCache == null) {
//                    return;
//                }
//                File file = new File(mDirCache);
//                if (!file.exists()) {
//                    file.mkdir();
//                }
//                AndFixPatchManager.addPatch(getPatchName());
                fix();

                break;
        }
    }

    /**
     * 获取patch文件
     *
     * @return
     */
    private String getPatchName() {
        return mDirCache.concat("ndk").concat(".apatch");
    }

    /**
     * 判断sdCard是否可用
     * <p>
     * getCacheDir()方法用于获取/data/data//cache目录
     * getFilesDir()方法用于获取/data/data//files目录
     *
     * @return
     */
    private String getmDirCache() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            /**
             * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，
             * 一般放一些长时间保存的数据
             通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，
             一般存放临时缓存数据.如果使用上面的方法，
             当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。
             */
            mDirCache = getExternalCacheDir().getAbsolutePath() + "/apatch/";
        }
        return mDirCache;
    }

    private void fix() {
        String path = getExternalCacheDir() + "/fix.apk";
        ThinkerManger.loadPatch(path);
    }
}
