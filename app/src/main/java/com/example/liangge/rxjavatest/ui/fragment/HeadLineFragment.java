package com.example.liangge.rxjavatest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guhongliang on 2018/3/5.
 */

public class HeadLineFragment extends Fragment {
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);
        String content = getArguments().getString(TabFragment.CONTENT);
        mTextView.setText(content);
        return mTextView;
    }
}
