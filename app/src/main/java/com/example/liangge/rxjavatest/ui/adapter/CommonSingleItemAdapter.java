package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;

import java.util.List;

/**
 * Created by guhongliang on 2017/10/16.
 */

public class CommonSingleItemAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public CommonSingleItemAdapter(List<String> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        String s = mList.get(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.call_phone_item, parent, false);
            holder.mContent = (TextView) convertView.findViewById(R.id.call_phone_item_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mContent.setText(s);
        return convertView;
    }

    class Holder {
        TextView mContent;
    }
}
