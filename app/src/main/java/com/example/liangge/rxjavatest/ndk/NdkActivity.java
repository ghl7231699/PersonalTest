package com.example.liangge.rxjavatest.ndk;

import android.graphics.Color;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.common.utils.Utils;
import com.example.liangge.rxjavatest.ndk.baseactivity.BaseNdkActivity;
import com.example.liangge.rxjavatest.thinker.ThinkerManger;
import com.example.liangge.rxjavatest.ui.view.UnreadMessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghl11 on 2017/11/5.
 */

public class NdkActivity extends BaseNdkActivity implements View.OnClickListener {
    private TextView mTv;
    private Button fix, calutor;

    private String mDirCache;

    private UnreadMessageView mMessageView;
    //    private LinearLayout mLayout;
    private HorizontalScrollView mWheelView;
    private LinearLayout mLinearLayout;
    private int screenWitdth;

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
//        mLayout = (LinearLayout) findViewById(R.id.ndk_ll_container);

        mWheelView = (HorizontalScrollView) findViewById(R.id.ndk_hs);
        mLinearLayout = (LinearLayout) findViewById(R.id.ndk_hs_ll);

        mMessageView.setContent("未读消息");
        mMessageView.setNum("8");
        mMessageView.setColor(Color.BLUE);

        fix.setOnClickListener(this);
        calutor.setOnClickListener(this);

//        addView();
//        addViews();
        addWheelView();

    }

    private void addWheelView() {
        screenWitdth = getResources().getDisplayMetrics().widthPixels;
        UnreadMessageView messageView;
//        TextView messageView;
        List<View> mViews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messageView = new UnreadMessageView(this);
//            messageView = new TextView(this);
//            messageView.setBackground(getDrawable(R.drawable.list_item_select));
            messageView.setBackground(getResources().getDrawable(R.drawable.list_item_select));
            messageView.setContent("房源审核失败" + i);
            messageView.setNum(String.valueOf(i));
            messageView.setColor(Color.BLUE);
//            messageView.setText("跟进提醒" + i);
//            messageView.setTextColor(Color.RED);
            mViews.add(messageView);
        }
        LinearLayout.LayoutParams childP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childP.gravity = Gravity.CENTER;
        if (mViews == null) {
            return;
        }
        View childAt;
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            //获取到每个item
            childAt = mViews.get(i);
            childAt.setLayoutParams(childP);
            //如果view被选中，则居中显示，除了i=0和i=childCount-1
            mLinearLayout.addView(childAt);
            if (childAt.isSelected()) {
                if (i != 0 && i != size - 1) {
                    int left = childAt.getLeft();//获取子空间距离父控件左侧的距离
                    int childWidth = childAt.getMeasuredWidth();//获取子控件的宽度
                    int x = left + childWidth / 2 - screenWitdth / 2;
                    mWheelView.smoothScrollTo(x, 0);
                }
            }
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChildView(v);
                }
            });
        }
    }

    private void setChildView(View v) {
        if (!v.isSelected()) {
            v.isSelected();
        }
        v.setBackground(getResources().getDrawable(R.drawable.list_item_select));
        int left = v.getLeft();//获取子空间距离父控件左侧的距离
        int childWidth = v.getMeasuredWidth();//获取子控件的宽度
        int x = left + childWidth / 2 - screenWitdth / 2;
        mWheelView.smoothScrollTo(x, 0);
    }

    private void addView() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginHorizontal = Utils.dp2px(this, 5);
        param.setMargins(marginHorizontal, 0, 0, 0);
        UnreadMessageView view;
        for (int i = 0; i < 10; i++) {
            View layout = LayoutInflater.from(this).inflate(R.layout.scroll_item, null);

            view = layout.findViewById(R.id.ndk_custom_item);
            if (i == 0) {
                view.setColor(Color.BLUE);
            } else {
                view.setColor(Color.BLACK);
            }
            view.setContent("消息" + i);
            view.setNum(String.valueOf(i));
            layout.setLayoutParams(param);
//            mLayout.addView(layout);
        }

    }

    @Override
    public void initData() {
        showToast();
    }

    /**
     * 自定义toast
     */
    private void showToast() {
//        View view = LayoutInflater.from(this).inflate(R.layout.loadding, null, false);
//        TextView content = view.findViewById(R.id.load_content);
//        content.setText("朱日和");
//        ToastUtils.showWidgetView(view);
        ToastUtils.showCenter(this, "我是个中间的toast");
//        Toast mToast = new Toast(this);
//        mToast.setGravity(Gravity.CENTER, 0, 0);
//        mToast.setDuration(Toast.LENGTH_SHORT);
//        mToast.setText("我是个中间的toast");
//        mToast.show();
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
