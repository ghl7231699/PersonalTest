package com.example.liangge.rxjavatest.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.FruitActivity;
import com.example.liangge.rxjavatest.ui.adapter.FruitsAdapter;
import com.example.liangge.rxjavatest.ui.fragment.basefragment.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 类名称：
 * 类描述：
 * 创建人：ghl
 * 创建时间：2018/3/5 下午5:53
 * 修改人：ghl
 * 修改时间：2018/3/5 下午5:53
 *
 * @version v1.0
 */

public class VideoFragment extends BaseFragment {
    private static SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Fruits[] mFruits = {new Fruits("Apple", R.mipmap.backup), new Fruits("Orange", R.mipmap.call),
            new Fruits("Pear", R.mipmap.friends),
            new Fruits("Cherry", R.mipmap.location),
            new Fruits("PineApple", R.mipmap.mail)};
    private List<Fruits> mFruitsList = new ArrayList<>();
    private FruitsAdapter mFruitAdapter;
    private DelayHandler mHandler;

    private static class DelayHandler extends Handler {
        private final WeakReference<VideoFragment> mWeakReference;

        public DelayHandler(VideoFragment fragment) {
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            VideoFragment vf = mWeakReference.get();
            super.handleMessage(msg);
            if (vf != null) {
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
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView() {
        mRefreshLayout = mActivity.findViewById(R.id.fg_video_sr);
        mRecyclerView = mActivity.findViewById(R.id.fg_video_rv);

        mHandler = new DelayHandler(this);
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

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setRecycleView();
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    private void setRecycleView() {
        initFruits();
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mRecyclerView.setLayoutManager(manager);
        mFruitAdapter = new FruitsAdapter(mActivity, mFruitsList);
        mFruitAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.fruit_image:
                        Fruits fruits = mFruitsList.get(position);
                        Intent intent = new Intent(mActivity, FruitActivity.class);
                        String name = fruits.getName();
                        intent.putExtra(FruitActivity.FRUIT_NAME, name);
                        int imageId = fruits.getImageId();
                        intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, imageId);
                        startActivity(intent);
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(mFruitAdapter);
    }

    private void initFruits() {
        mFruitsList.clear();
        for (int i = 0; i < 20; i++) {
            Random r = new Random();
            int index = r.nextInt(mFruits.length);
            mFruitsList.add(mFruits[index]);
        }
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
