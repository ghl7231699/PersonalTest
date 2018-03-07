package com.example.liangge.rxjavatest.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.adapter.VideoAdapter;
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
    private Fruits[] mFruits = {new Fruits("小李", R.mipmap.kdbag), new Fruits("小王", R.mipmap.collect),
            new Fruits("shirley", R.mipmap.expression),
            new Fruits("小马", R.mipmap.kdbag),
            new Fruits("小刘", R.mipmap.friends)};
    private List<Fruits> mFruitsList = new ArrayList<>();
    private VideoAdapter mFruitAdapter;
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
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mFruitAdapter = new VideoAdapter(mFruitsList, mActivity);
        mRecyclerView.setAdapter(mFruitAdapter);
    }

    private void initFruits() {
        mFruitsList.clear();
        for (int i = 0; i < 15; i++) {
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
