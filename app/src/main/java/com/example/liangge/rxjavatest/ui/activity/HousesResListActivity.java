package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ui.adapter.HouseResAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HousesResListActivity extends AppCompatActivity {
    @BindView(R.id.house_list_lv)
    ListView mHouseListLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("" + i);
        }
        HouseResAdapter adapter = new HouseResAdapter(mList, this, getSupportFragmentManager());
        mHouseListLv.setAdapter(adapter);

        mHouseListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HousesResListActivity.this, ResListActivity.class);
                startActivity(intent);
            }
        });
    }
}
