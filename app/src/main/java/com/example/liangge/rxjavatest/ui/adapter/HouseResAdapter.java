package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.ToastUtils;
import com.example.liangge.rxjavatest.ui.activity.ResListActivity;
import com.example.liangge.rxjavatest.ui.activity.HousesResListActivity;
import com.example.mylibrary.BaseNiceDialog;
import com.example.mylibrary.NiceDialog;
import com.example.mylibrary.ViewConvertListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by guhongliang on 2017/10/16.
 */

public class HouseResAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;
    private FragmentManager mFragmentManager;

    public HouseResAdapter(List<String> list, Context context, FragmentManager fragmentManager) {
        mList = list;
        mContext = context;
        mFragmentManager = fragmentManager;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.house_list_item, parent, false);
            holder.phone = (TextView) convertView.findViewById(R.id.house_list_item_phone);
            holder.add = (TextView) convertView.findViewById(R.id.house_list_item_add);
            holder.more = (TextView) convertView.findViewById(R.id.house_list_item_houses_have_more);
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.house_list_item_more_ll);
            holder.mPhoneLinearLayout = (LinearLayout) convertView.findViewById(R.id.house_list_item_phone_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mContext instanceof ResListActivity) {
            holder.mLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mLayout.setVisibility(View.GONE);
        }
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                        .setLayoutId(R.layout.call_phone)
                        .setListener(new ViewConvertListener() {
                            @Override
                            public void convertView(com.example.mylibrary.ViewHolder viewHolder, final BaseNiceDialog dialog) {
                                final ListView mLv = viewHolder.getView(R.id.call_phone_lv);
                                final List<String> list = new ArrayList<>();
                                list.add("拨打电话");
                                Random random = new Random();
                                random.nextInt(10);
                                for (int i = 0; i < 5; i++) {
                                    list.add("三强（男）  电话号码为 010-" + random.nextInt(10));
                                }
                                list.add("取消");
                                mLv.setAdapter(new CommonSingleItemAdapter(list, mContext));
                                mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (position == 0) {
                                            mLv.setEnabled(false);
                                        } else {
                                            mLv.setEnabled(true);
                                        }
                                        if (position == list.size() - 1) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                            }
                        })
                        .setShowBottom(false)
                        .setDimAmount(0.3f)
                        .setHeight(200)
                        .setMargin(20)
                        .show(mFragmentManager);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                        .setLayoutId(R.layout.stay_house_resource)
                        .setListener(new ViewConvertListener() {
                            @Override
                            public void convertView(com.example.mylibrary.ViewHolder viewHolder, final BaseNiceDialog dialog) {
                                ListView mLv = viewHolder.getView(R.id.stay_house_resource_lv);
                                List<String> list = new ArrayList<>();
                                for (int i = 0; i < 5; i++) {
                                    list.add("金山寺,第" + i + "个");
                                }
                                mLv.setAdapter(new StayHouseResAdapter(list, mContext));
                                viewHolder.setOnClickListener(R.id.stay_house_resource_close, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setShowBottom(true)
                        .setHeight(400)
                        .setOutCancel(false)
                        .show(mFragmentManager);
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HousesResListActivity.class);
                mContext.startActivity(intent);
            }
        });

        holder.mPhoneLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toast("点击了无效区域");
            }
        });
        return convertView;
    }

    class ViewHolder {
        LinearLayout mLayout;
        LinearLayout mPhoneLinearLayout;
        TextView price;
        TextView type;
        TextView num;
        TextView content;
        TextView houses;
        TextView street;
        TextView moreContent;
        TextView more;
        TextView add;
        TextView phone;
    }
}
