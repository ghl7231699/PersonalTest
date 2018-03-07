package com.example.liangge.rxjavatest.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.HomePageBean;

import java.util.List;

/**
 * Created by guhongliang on 2018/3/6.
 */

public class HomePageAdapter extends BaseQuickAdapter<HomePageBean, BaseViewHolder> {
    public HomePageAdapter(List<HomePageBean> data) {
        super(R.layout.head_portrait_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageBean item) {
        helper.setVisible(R.id.fg_mine_qr_code_iv, false);
        helper.setVisible(R.id.fg_mine_head_time, true);

        helper.setText(R.id.fg_mine_name_tv, item.getUserName());
        helper.setText(R.id.fg_mine_num_tv, item.getContent());
        helper.setText(R.id.fg_mine_head_time, item.getDate());

        helper.setImageResource(R.id.fg_mine_head_iv, item.getImageId());
    }
}
