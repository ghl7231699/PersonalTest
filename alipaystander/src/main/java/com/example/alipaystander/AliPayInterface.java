package com.example.alipaystander;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by guhongliang on 2017/8/26.
 */

public interface AliPayInterface {
    public void onCreate(Bundle saveInstance);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public boolean onTouchEvent(MotionEvent event);

    public void onBackPressed();

    /**
     * 主程序传入的Context
     */
    public void attach(Activity activity);
}
