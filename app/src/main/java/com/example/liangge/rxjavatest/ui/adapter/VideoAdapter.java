package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.constant.Fruits;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;

import java.util.List;

/**
 * Created by guhongliang on 2018/3/7.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TITLE = 0x01;
    private static final int view_type_item = 0x02;
    private static final int view_type_foot = 0x03;
    private List<Fruits> mList;
    private Context mContext;

    public VideoAdapter(List<Fruits> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == view_type_foot) {
            view = LayoutInflater.from(mContext).inflate(R.layout.foot_view, parent, false);
            return new FootViewHolder(view);
        } else if (viewType == view_type_item) {
            view = LayoutInflater.from(mContext).inflate(R.layout.iv_tv_item, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Fruits fruits;
        if (holder instanceof ItemViewHolder) {
            fruits = mList.get(position);
            ((ItemViewHolder) holder).iv.setImageResource(fruits.getImageId());
            ((ItemViewHolder) holder).name.setText(fruits.getName());
            ((ItemViewHolder) holder).mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showCenter(mContext, fruits.getName());
                }
            });
        } else if (holder instanceof FootViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return view_type_foot;
        }
        return view_type_item;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView iv;
        LinearLayout mLinearLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.iv_tv_item_tv);
            iv = itemView.findViewById(R.id.iv_tv_item_iv);
            mLinearLayout = itemView.findViewById(R.id.iv_tv_item_container);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);

        }
    }
}
