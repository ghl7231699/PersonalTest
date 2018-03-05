package com.example.liangge.rxjavatest.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.adapter.SlideAdapter;
import com.example.liangge.rxjavatest.ui.fragment.basefragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TabFragment extends BaseFragment {
    public static final String CONTENT = "content";
    RecyclerView mRecyclerView;
    private List<Fruit> mList;
    private String[] s = {"apple", "orange", "banner"};


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
        final SlideAdapter adapter = new SlideAdapter(getActivity(), mList);
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

    @Override
    public void processClick(View v) {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

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
}
