package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.HomePageBean;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.navigationbarlibrary.UIUtils;

import java.util.List;

/**
 * Created by guhongliang on 2018/3/7.
 */

public class DiscoveryAdapter extends BaseQuickAdapter<HomePageBean, BaseViewHolder> {
    private Context mContext;
    private LinearLayout mContainer;

    public DiscoveryAdapter(Context context, List<HomePageBean> data) {
        super(R.layout.iv_tv_item_discovery, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageBean item) {
        mContainer = helper.getView(R.id.fg_dis_item_container);
        if (item != null) {
            addView(item);
        }
//        helper.setText(R.id.iv_tv_item_dis_tv, item.getContent());
//        helper.setImageResource(R.id.iv_tv_item_dis_iv, item.getImageId());
    }

    private void addView(HomePageBean it) {
        int num = it.getPosition();
        final List<HomePageBean> home = it.getHome();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2Px(mContext, 48));
        if (home != null && !home.isEmpty()) {
            for (int i = 0; i < home.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.iv_tv_item, null);
                ImageView iv = view.findViewById(R.id.iv_tv_item_iv);
                TextView tv = view.findViewById(R.id.iv_tv_item_tv);
                iv.setImageResource(home.get(i).getImageId());
                tv.setText(home.get(i).getContent());
                view.setLayoutParams(params);
                mContainer.addView(view, params);
                final int x = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showCenter(mContext, "点击了" + home.get(x).getContent());
                    }
                });
            }
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.iv_tv_item, null);
            ImageView iv = view.findViewById(R.id.iv_tv_item_iv);
            TextView tv = view.findViewById(R.id.iv_tv_item_tv);
            iv.setImageResource(it.getImageId());
            tv.setText(it.getContent());
            view.setLayoutParams(params);
            mContainer.addView(view, params);
        }
    }

}
