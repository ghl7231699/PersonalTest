package com.example.liangge.rxjavatest.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.HomePageBean;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.adapter.DiscoveryAdapter;
import com.example.liangge.rxjavatest.ui.fragment.basefragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：
 * 类描述：发现fragment
 *
 * @version v1.0
 */

public class DiscoveryFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<HomePageBean> list = new ArrayList<>();
    private DiscoveryAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void initView() {
        mRecyclerView = mActivity.findViewById(R.id.fg_dis_rv);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        init();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new DiscoveryAdapter(mActivity, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    private void init() {
        list.clear();
        HomePageBean friend = new HomePageBean("朋友圈", R.mipmap.friends);
        list.add(friend);

        HomePageBean bean2 = new HomePageBean();
        List<HomePageBean> l1 = new ArrayList<>();
        HomePageBean b1 = new HomePageBean();
        b1.setContent("扫一扫");
        b1.setImageId(R.mipmap.sao);
        l1.add(b1);
        HomePageBean b2 = new HomePageBean();
        b2.setContent("摇一摇");
        b2.setImageId(R.mipmap.yao);
        l1.add(b2);

        bean2.setHome(l1);
        list.add(bean2);


        HomePageBean bean3 = new HomePageBean();
        List<HomePageBean> l2 = new ArrayList<>();
        HomePageBean b3 = new HomePageBean();
        b3.setContent("附近的人");
        b3.setImageId(R.mipmap.around);
        l2.add(b3);
        HomePageBean bb3 = new HomePageBean();
        bb3.setContent("漂流瓶");
        bb3.setImageId(R.mipmap.bottle);
        l2.add(bb3);

        bean3.setHome(l2);
        list.add(bean3);

        HomePageBean bean4 = new HomePageBean();
        List<HomePageBean> l3 = new ArrayList<>();
        HomePageBean b4 = new HomePageBean();
        b4.setContent("购物");
        b4.setImageId(R.mipmap.shopping);
        l3.add(b4);
        HomePageBean bb4 = new HomePageBean();
        bb4.setContent("游戏");
        bb4.setImageId(R.mipmap.game);
        l3.add(bb4);

        bean4.setHome(l3);
        list.add(bean4);
    }
}
