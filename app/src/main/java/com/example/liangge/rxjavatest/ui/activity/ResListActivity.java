package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ui.adapter.HouseResAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guhongliang on 2017/10/16.
 */

public class ResListActivity extends AppCompatActivity {
    @BindView(R.id.house_list_lv)
    ListView mHouseListLv;
    List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("" + i);
        }
        HouseResAdapter adapter = new HouseResAdapter(mList, this, getSupportFragmentManager());
        mHouseListLv.setAdapter(adapter);
    }
}
