package com.example.liangge.rxjavatest.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CoordinateActivity extends Activity {

    private Fruits[] mFruits = {new Fruits("Apple", R.mipmap.backup), new Fruits("Orange", R.mipmap.call),
            new Fruits("Pear", R.mipmap.friends),
            new Fruits("Cherry", R.mipmap.location),
            new Fruits("PineApple", R.mipmap.mail)};
    private List<Fruits> mFruitsList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ListView mListView;
    private Context instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
        instance = this;
        initView();
        initFruits();
        initData();
    }

    /**
     * 1、打开FEATURE_CONTENT_TRANSITIONS开关，默认为打开的
     */
    private void initWindow() {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        /**
         * 2、设置ShareElement外其他view的退出方式（左边划出）
         */
        getWindow().setExitTransition(new Slide(Gravity.START));
        Fade fade = new Fade();//渐隐
        fade.setDuration(500);
        Slide slide = new Slide(Gravity.END);//右侧平移
        slide.setDuration(500);
        Explode explode = new Explode();//展开回收
        getWindow().setReenterTransition(slide);
        getWindow().setExitTransition(explode);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);
    }

    private void initData() {
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void initFruits() {
        mFruitsList.clear();
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int index = r.nextInt(4);
            mFruitsList.add(mFruits[index]);
        }
    }

    private void initView() {
        mListView = findViewById(R.id.lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gotoDetailActivity(true);
                } else {
                    gotoDetailActivity(false);
                }
            }
        });
    }

    /**
     * 生成Bundle 然后startActivity
     */
    private void gotoDetailActivity(boolean b) {
        Intent intent;
        if (b) {
            intent = new Intent(this, CardViewActivity.class);
        } else {
            intent = new Intent(this, MaterialDesignActivity.class);
        }
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(intent, options.toBundle());
    }

    class MyAdapter extends BaseAdapter {

        public MyAdapter() {

        }

        @Override
        public int getCount() {
            return mFruitsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mFruitsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = LayoutInflater.from(instance).inflate(R.layout.fruit_view, parent, false);
            ImageView iv = inflate.findViewById(R.id.fruit_image);
            TextView tv = inflate.findViewById(R.id.fruit_content);


            iv.setImageResource(mFruitsList.get(position).getImageId());
            tv.setText(mFruitsList.get(position).getName());

            return inflate;
        }
    }
}
