package com.example.mylibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guhongliang on 2017/9/5.
 */

public abstract class BaseNiceFrament extends DialogFragment {
    public abstract int intLayoutId();
    public abstract void  converView(ViewHolder viewHolder,);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(intLayoutId(), container, false);
        return view;
    }
}
