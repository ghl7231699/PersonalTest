package com.example.mylibrary;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;


/**
 * Created by guhongliang on 2017/9/5.
 */

public class NiceDialog extends BaseNiceDialog {
    private ViewConvertListener mListener;
    private static final String LISTENER = "listener";

    public static NiceDialog init() {
        return new NiceDialog();
    }

    @Override
    public int intLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(ViewHolder viewHolder, BaseNiceDialog dialog) {
        if (mListener != null) {
            mListener.convertView(viewHolder, dialog);
        }
    }

    public NiceDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public NiceDialog setListener(ViewConvertListener convertListener) {
        this.mListener = convertListener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mListener = (ViewConvertListener) savedInstanceState.getSerializable(LISTENER);
        }
    }

    /**
     * 保存接口
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LISTENER, mListener);
    }
}
