package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;

import java.util.List;

/**
 * Created by guhongliang on 2018/3/6.
 */

public class MineAdapter extends BaseQuickAdapter<Fruits, BaseViewHolder> {
    private Context mContext;

    public MineAdapter(Context context, List<Fruits> data) {
        super(R.layout.iv_tv_item, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Fruits item) {
        helper.setText(R.id.iv_tv_item_tv, item.getName());
//        Glide.with(mContext).load(item.getImageId()).crossFade()
//                .into((ImageView) helper.getView(R.id.iv_tv_item_iv));
        helper.setImageResource(R.id.iv_tv_item_iv, item.getImageId());

    }
}
