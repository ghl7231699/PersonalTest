package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.ui.view.SlidingMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guhongliang on 2017/8/30.
 */

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideVh> {

    private Context mContext;
    private List<Fruit> mList;
    private SlidingMenu mSlidingMenu;
    public static final String ZD = "置顶";
    public static final String ZD_CANCEL = "取消置顶";
    private MenuItemOnClickListener mListener;

    public SlideAdapter(Context context, List<Fruit> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public SlideVh onCreateViewHolder(ViewGroup parent, int viewType) {
        SlideVh slideVh;
        if (mContext == null) {
            mContext = parent.getContext();
        }
        slideVh = new SlideVh(LayoutInflater.from(mContext).inflate(R.layout.slide_activity_item_layout, parent, false));
        return slideVh;
    }

    @Override
    public void onBindViewHolder(final SlideVh holder, final int position) {
        Fruit f = mList.get(position);
        holder.mSlideItemIv.setText(f.getName());
        if (f.isTop()) {
            holder.mSlideItemTv.setText(ZD_CANCEL);
            holder.mSlideItemTv.setBackgroundColor(Color.BLUE);
        } else {
            closeOpenMenu();
            holder.mSlideItemTv.setText(ZD);
            holder.mSlideItemTv.setBackgroundColor(Color.RED);

        }
        holder.mSlideItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOpenMenu();
                boolean top;
                String t = holder.mSlideItemTv.getText().toString();
                if (t.equals(ZD)) {

                    top = true;
                } else {
//                    holder.mSlideItemTv.setText(ZD);
//                    holder.mSlideItemTv.setBackgroundColor(Color.RED);
                    top = false;
                }
                if (mListener != null) {
                    mListener.onMenuClick(position, top);
                }
            }
        });
    }

    public void setOnClick(MenuItemOnClickListener click) {
        this.mListener = click;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface MenuItemOnClickListener {
        void onMenuClick(int position, boolean top);
    }

    /**
     * 作者：ghl
     * 描述：记录打开抽屉的view
     * 创建时间：
     *
     * @Params:
     * @Return:
     */
    public void holdOpenMenu(SlidingMenu menu) {
        mSlidingMenu = menu;
    }

    public void closeOpenMenu() {
        if (mSlidingMenu != null && mSlidingMenu.isOpen()) {
            mSlidingMenu.closeMenu();
        }
    }

    class SlideVh extends RecyclerView.ViewHolder {
        @BindView(R.id.slide_item_iv)
        TextView mSlideItemIv;
        @BindView(R.id.slide_item_content)
        LinearLayout mSlideItemContent;
        @BindView(R.id.slide_item_tv)
        TextView mSlideItemTv;
        @BindView(R.id.slide_item_menu)
        LinearLayout mSlideItemMenu;

        public SlideVh(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
