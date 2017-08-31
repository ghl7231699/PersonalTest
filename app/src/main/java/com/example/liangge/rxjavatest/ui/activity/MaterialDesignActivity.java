package com.example.liangge.rxjavatest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;
import com.example.liangge.rxjavatest.ui.adapter.FruitsAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaterialDesignActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Fruits[] mFruits = {new Fruits("Apple", R.mipmap.backup), new Fruits("Orange", R.mipmap.call),
            new Fruits("Pear", R.mipmap.friends),
            new Fruits("Cherry", R.mipmap.location),
            new Fruits("PineApple", R.mipmap.mail)};
    private List<Fruits> mFruitsList = new ArrayList<>();
    //    private FruitAdapter mFruitAdapter;
    private FruitsAdapter mFruitAdapter;
    private static SwipeRefreshLayout mRefreshLayout;
    private MyHandler mHandler;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private static class MyHandler extends Handler {
        private final WeakReference<MaterialDesignActivity> mWeakReference;

        public MyHandler(MaterialDesignActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MaterialDesignActivity activity = mWeakReference.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        if (mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        initView();
        setActionBar();
        setNavigationListener();
        setRecycleView();
    }

    private void setRecycleView() {
        initFruits();
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rv.setLayoutManager(manager);
        mFruitAdapter = new FruitsAdapter(this, mFruitsList);
        mFruitAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.fruit_image:
                        Fruits fruits = mFruitsList.get(position);
                        Intent intent = new Intent(MaterialDesignActivity.this, FruitActivity.class);
                        String name = fruits.getName();
                        intent.putExtra(FruitActivity.FRUIT_NAME, name);
                        Log.d("FruitAdapter", "onClick: " + name);
                        int imageId = fruits.getImageId();
                        intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, imageId);
                        startActivity(intent);
                        break;
                }
            }
        });
        rv.setAdapter(mFruitAdapter);
    }

    private void initFruits() {
        mFruitsList.clear();
        for (int i = 0; i < 50; i++) {
            Random r = new Random();
            int index = r.nextInt(mFruits.length);
            mFruitsList.add(mFruits[index]);
        }
    }

    private void setNavigationListener() {
        mNavigationView.setCheckedItem(R.id.nav_call);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swi_re_l);
        mHandler = new MyHandler(this);
        mRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mRefreshLayout.setDistanceToTriggerSync(300);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadThread().start();
            }
        });
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked BackUp", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            default:
        }
        return true;
    }

    class LoadThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendEmptyMessage(1);
        }
    }
}
