package com.example.liangge.rxjavatest.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.liangge.rxjavatest.ui.adapter.SlideAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class SlideActivity extends BaseActivity {

    @BindView(R.id.activity_slide_rv)
    RecyclerView mRecyclerView;
    private List<Fruit> mList;
    private String[] s = {"apple", "orange", "banner"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_slide;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void initData() {
        getData();
        final SlideAdapter adapter = new SlideAdapter(this, mList);
        adapter.setOnClick(new SlideAdapter.MenuItemOnClickListener() {
            @Override
            public void onMenuClick(int position, boolean top) {
                Fruit fruit = mList.get(position);
                fruit.setTop(top);
                mList.set(0, fruit);
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(2);
            Fruit f = new Fruit();
            f.setName(s[nextInt]);
            mList.add(f);
        }
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }


}
