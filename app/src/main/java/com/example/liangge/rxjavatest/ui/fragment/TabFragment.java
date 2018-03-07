package com.example.liangge.rxjavatest.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.common.constant.HomePageBean;
import com.example.liangge.rxjavatest.common.utils.TimeUtil;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.adapter.HomePageAdapter;
import com.example.liangge.rxjavatest.ui.adapter.SlideAdapter;
import com.example.liangge.rxjavatest.ui.fragment.basefragment.BaseFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TabFragment extends BaseFragment {
    public static final String CONTENT = "content";
    RecyclerView mRecyclerView;
    private List<HomePageBean> mList;
    private String[] name = {"浪里个浪", "科比", "川建国", "局座"};
    private String[] content = {"蒋二:什么时候开始活动", "[图片]", "把哥发型整好点，我要推自拍涨粉，普京说的绝招....", "来自东方的神秘力量"};
    private int[] id = {R.mipmap.head, R.mipmap.kb, R.mipmap.cp, R.mipmap.jz};

    @Override
    public int getLayoutId() {
        return R.layout.activity_slide;
    }

    public void initView() {
        mRecyclerView = rootView.findViewById(R.id.activity_slide_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void initListener() {

    }

    public void initData() {
        getData();

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        HomePageAdapter adapter = new HomePageAdapter(mList);

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }


    private void getData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(3);
            HomePageBean bean = new HomePageBean();
            bean.setUserName(name[i]);
            bean.setContent(content[i]);
            bean.setImageId(id[i]);
            bean.setDate(TimeUtil.getTimeStamp());
            mList.add(bean);
        }
    }
}
