package com.example.liangge.rxjavatest.ui.fragment.basefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.rxjavatest.R;

/**
 * Created by guhongliang on 2017/9/5.
 * 自定义fragment继承DialogFragment
 */

public abstract class BaseNiceFrgment extends DialogFragment {
    public abstract int intLayout();
    public abstract void convertView();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
