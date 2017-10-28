package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;

import java.util.List;

/**
 * Created by guhongliang on 2017/10/13.
 */

public class StayHouseResAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public StayHouseResAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.stay_house_resource_item, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.stay_house_resource_item_content);
            holder.mButton = (TextView) convertView.findViewById(R.id.stay_house_resource_item_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.mTextView.setText(mList.get(position));
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toast("添加到了待约房源列表");
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mTextView;
        TextView mButton;
    }

}
