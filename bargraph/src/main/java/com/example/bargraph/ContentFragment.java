package com.example.bargraph;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guhongliang on 2017/9/19.
 */

public class ContentFragment extends Fragment {
    private static final String ARG_TIMELINE_TYPE = "ARG_TIMELINE_TYPE";
    private TextView mContent;
    private int type;

    public ContentFragment() {
    }

    public static ContentFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TIMELINE_TYPE, type);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getInt(ARG_TIMELINE_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_fragment, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
