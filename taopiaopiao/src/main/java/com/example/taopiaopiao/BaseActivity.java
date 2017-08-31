package com.example.taopiaopiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.example.alipaystander.AliPayInterface;

/**
 * Created by guhongliang on 2017/8/26.
 */

public class BaseActivity extends AppCompatActivity implements AliPayInterface {
    protected Activity that;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (that == null) {
            super.setContentView(layoutResID);
        }
        that.setContentView(layoutResID);
    }

    @Override
    public View findViewById(@IdRes int id) {
        if (that == null) {
            return super.findViewById(id);
        }
        return that.findViewById(id);
    }

    @Override
    public ClassLoader getClassLoader() {
        if (that == null) {
            return super.getClassLoader();
        }
        return that.getClassLoader();
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if (that == null) {
            return super.getLayoutInflater();
        }
        return that.getLayoutInflater();
    }

    @Override
    public Window getWindow() {
        if (that == null) {
            return super.getWindow();
        }
        return that.getWindow();

    }

    @Override
    public void onCreate(Bundle saveInstance) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void attach(Activity activity) {
        this.that = activity;
    }

    @Override
    public void startActivity(Intent intent) {
        Intent newIntent = new Intent();
        newIntent.putExtra("className", intent.getComponent().getClassName());
        that.startActivity(newIntent);
    }
}
