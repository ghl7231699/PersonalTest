package com.example.liangge.rxjavatest.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.HomeActivity;
import com.example.liangge.rxjavatest.ui.adapter.MineAdapter;
import com.example.liangge.rxjavatest.ui.fragment.basefragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2018/3/6.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private LinearLayout mLayout;
    private TextView wxNum;
    private TextView wxName;
    private Fruits[] mFruits = {new Fruits("收藏", R.mipmap.collect),
            new Fruits("相册", R.mipmap.picture),
            new Fruits("卡包", R.mipmap.kdbag),
            new Fruits("表情", R.mipmap.expression)};
    private List<Fruits> mFruitsList = new ArrayList<>();
    private MineAdapter mFruitAdapter;

    private LinearLayout mSet;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        mRecyclerView = mActivity.findViewById(R.id.fg_mine_mine_rlv);
        mLayout = mActivity.findViewById(R.id.fg_mine_ll);
        wxNum = mActivity.findViewById(R.id.fg_mine_num_tv);
        wxName = mActivity.findViewById(R.id.fg_mine_name_tv);
        mSet = mActivity.findViewById(R.id.fg_mine_setting);

        mSet.setOnClickListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String text = String.format(mActivity.getResources().getString(R.string.wx_num), "ghl1149660196");
        wxNum.setText(text);
        init();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置RecyclerView 布局
        mRecyclerView.setLayoutManager(manager);
        mFruitAdapter = new MineAdapter(mActivity, mFruitsList);
        mFruitAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.fruit_image:
//                        Fruits fruits = mFruitsList.get(position);
//                        Intent intent = new Intent(mActivity, FruitActivity.class);
//                        String name = fruits.getName();
//                        intent.putExtra(FruitActivity.FRUIT_NAME, name);
//                        int imageId = fruits.getImageId();
//                        intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, imageId);
//                        startActivity(intent);
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(mFruitAdapter);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        mFruitsList.clear();
        for (int i = 0; i < 4; i++) {
            mFruitsList.add(mFruits[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fg_mine_setting:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
