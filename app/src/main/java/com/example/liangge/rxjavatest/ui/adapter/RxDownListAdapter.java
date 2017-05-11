package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;

import java.util.List;

/**
 * Created by guhongliang on 2017/5/8.
 */

public class RxDownListAdapter extends BaseQuickAdapter<Fruit, BaseViewHolder> {
    private Context mContext;

    public RxDownListAdapter(Context context, List<Fruit> data) {
        super(R.layout.rx_down_list_item, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Fruit item) {
        helper.setText(R.id.rx_down_load_content, item.getName());
        helper.setBackgroundRes(R.id.rx_down_load_item_img, item.getImageId());
        Glide.with(mContext).load(item.getImageId()).into((ImageView) helper.getView(R.id.rx_down_load_item_img));
        helper.addOnClickListener(R.id.rx_down_load_btn);
    }
}
