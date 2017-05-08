package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;

import java.util.List;

/**
 * Created by guhongliang on 2017/4/17.
 */

public class FruitsAdapter extends BaseQuickAdapter<Fruits,BaseViewHolder> {
    private Context mContext;
    public FruitsAdapter(Context context,List<Fruits> data) {
        super(R.layout.fruit_view, data);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Fruits item) {
            helper.setText(R.id.fruit_content,item.getName());
        Glide.with(mContext).load(item.getImageId()).crossFade().into((ImageView) helper.getView(R.id.fruit_image));
    }
}
