package com.example.liangge.rxjavatest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.ui.activity.FruitActivity;
import com.example.liangge.rxjavatest.common.constant.Fruits;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liangge on 2017/4/3.
 * 水果adapter
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context mContext;
    private List<Fruits> mFruitsList;

    public FruitAdapter(Context context, List<Fruits> fruitsList) {
        mContext = context;
        mFruitsList = fruitsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fruit_view, parent, false));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruits fruits = mFruitsList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                String name = fruits.getName();
                intent.putExtra(FruitActivity.FRUIT_NAME, name);
                Log.d("FruitAdapter", "onClick: "+name);
                int imageId = fruits.getImageId();
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, imageId);
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Fruits fruits = mFruitsList.get(position);
            Log.d("--------->", "onBindViewHolder: " + fruits.getName());
            String name = fruits.getName();
            holder.mFruitContent.setText(name);
            int imageId = fruits.getImageId();
            Glide.with(mContext).load(imageId).into(holder.mFruitImage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mFruitsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        @BindView(R.id.fruit_image)
        ImageView mFruitImage;
        @BindView(R.id.fruit_content)
        TextView mFruitContent;

        ImageView fruitImage;
        TextView fruitContent;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            ButterKnife.bind(this, itemView);
//            fruitImage= (ImageView) itemView.findViewById(R.id.fruit_image);
//            fruitContent= (TextView) itemView.findViewById(R.id.fruit_content);
        }
    }

}
