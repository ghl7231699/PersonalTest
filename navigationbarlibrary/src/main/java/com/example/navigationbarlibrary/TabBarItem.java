package com.example.navigationbarlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * 作者：ghl
 * 描述：底部导航childItem
 * 创建时间：2018/3/5 上午11:12
 *
 * @Params:
 * @Return:
 */

public class TabBarItem extends LinearLayout {
    private Context mContext;

    private int mIconNormalResourceId;//普通状态图标的资源id
    private int mIconSelectedResourceId;//选中状态图标的资源id
    private String text;//文本内容
    private int textSize = 12;//默认为12sp
    private int textColorNormal = 0xFF999999;    //描述文本的默认显示颜色
    private int mTextColorSelected = 0xFF46C01B;  //述文本的默认选中显示颜色
    private int mMarginTop = 0;//文字和图标的距离,默认0dp
    private boolean mOpenTouchBg = false;// 是否开启触摸背景，默认关闭
    private Drawable mTouchDrawable;//触摸时的背景
    private int mIconWidth;//图标的宽度
    private int mIconHeight;//图标的高度
    private int mItemPadding;//BottomBarItem的padding
    private int mUnreadTextSize = 8; //未读数默认字体大小10sp
    private int mMsgTextSize = 6; //消息默认字体大小6sp
    private int unreadNumThreshold = 99;

    private ImageView mImageView;
    private TextView mTvUnread;
    private TextView mTvNotify;
    private TextView mTvMsg;
    private TextView mTextView;

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTvUnread() {
        return mTvUnread;
    }

    public TextView getTvNotify() {
        return mTvNotify;
    }

    public TextView getTvMsg() {
        return mTvMsg;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setIconNormalResourceId(int iconNormalResourceId) {
        mIconNormalResourceId = iconNormalResourceId;
    }

    public void setIconSelectedResourceId(int iconSelectedResourceId) {
        mIconSelectedResourceId = iconSelectedResourceId;
    }

    public TabBarItem(Context context) {
        this(context, null);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarItem);
        mIconNormalResourceId = ta.getResourceId(R.styleable.BottomBarItem_iconNormal, -1);
        mIconSelectedResourceId = ta.getResourceId(R.styleable.BottomBarItem_iconSelected, -1);

        text = ta.getString(R.styleable.BottomBarItem_itemText);
        textSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemTextSize, UIUtils.dip2Px(mContext, textSize));

        textColorNormal = ta.getColor(R.styleable.BottomBarItem_textColorNormal, textColorNormal);
        mTextColorSelected = ta.getColor(R.styleable.BottomBarItem_textColorSelected, mTextColorSelected);

        mMarginTop = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemMarginTop, mMarginTop);

        mOpenTouchBg = ta.getBoolean(R.styleable.BottomBarItem_openTouchBg, false);
        mTouchDrawable = ta.getDrawable(R.styleable.BottomBarItem_touchDrawable);

        mIconWidth = ta.getDimensionPixelSize(R.styleable.BottomBarItem_iconWidth, 0);
        mIconHeight = ta.getDimensionPixelSize(R.styleable.BottomBarItem_iconHeight, 0);
        mItemPadding = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemPadding, 0);

        mUnreadTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_unreadTextSize, mUnreadTextSize);
        unreadNumThreshold = ta.getInteger(R.styleable.BottomBarItem_unreadThreshold, unreadNumThreshold);

        ta.recycle();

        checkValues();
        init();
    }

    private void checkValues() {
        if (mIconNormalResourceId == -1) {
            throw new IllegalArgumentException("您还没有设置默认状态下的图标，请指定iconNormal的图标");
        }
        if (mIconSelectedResourceId == -1) {
            throw new IllegalStateException("您还没有设置选中状态下的图标，请指定iconSelected的图标");
        }

        if (mOpenTouchBg && mTouchDrawable == null) {
            //如果有开启触摸背景效果但是没有传对应的drawable
            throw new IllegalStateException("开启了触摸效果，但是没有指定touchDrawable");
        }
    }

    /**
     * 初始化操作
     */
    @SuppressLint("NewApi")
    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        View view = View.inflate(mContext, R.layout.item_bottom_bar, null);
        if (mItemPadding != 0) {
            view.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }

        mImageView = view.findViewById(R.id.iv_icon);
        mTvUnread = view.findViewById(R.id.tv_unred_num);
        mTvMsg = view.findViewById(R.id.tv_msg);
        mTvNotify = view.findViewById(R.id.tv_point);
        mTextView = view.findViewById(R.id.tv_text);

        mImageView.setImageResource(mIconNormalResourceId);
        //如果ImageView设置了宽、高
        if (mIconWidth != 0 && mIconHeight != 0) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
            params.width = mIconWidth;
            params.height = mIconHeight;
            mImageView.setLayoutParams(params);
        }

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);//设置默认字体大小
        mTvUnread.setTextSize(TypedValue.COMPLEX_UNIT_SP, mUnreadTextSize);//设置未读消息字体

        mTextView.setTextColor(textColorNormal);
        mTextView.setText(text);

        LayoutParams layoutParams = (LayoutParams) mTextView.getLayoutParams();
        layoutParams.topMargin = mMarginTop;
        mTextView.setLayoutParams(layoutParams);

        if (mOpenTouchBg) {
            //如果开启了触摸背景
            setBackground(mTouchDrawable);
        }

        addView(view);
    }

    public void setStatus(boolean isSelected) {
        mImageView.setImageDrawable(getResources().getDrawable(isSelected ? mIconSelectedResourceId : mIconNormalResourceId));
        mTextView.setTextColor(isSelected ? mTextColorSelected : textColorNormal);
    }

    private void setTvVisible(TextView tv) {
        //设置为不可见
        mTvUnread.setVisibility(GONE);

        tv.setVisibility(VISIBLE);
    }

    public int getUnreadNumThreshold() {
        return unreadNumThreshold;
    }

    public void setUnreadNumThreshold(int unreadNumThreshold) {
        this.unreadNumThreshold = unreadNumThreshold;
    }

    /**
     * 设置未读消息数  当消息大于最大值时，则显示最大值+
     *
     * @param unreadNum
     */
    public void setUnreadNum(int unreadNum) {
        setTvVisible(mTvUnread);
        if (unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (unreadNum <= 99) {
            mTvUnread.setVisibility(VISIBLE);
            mTvUnread.setText(String.valueOf(unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadNumThreshold));
        }
    }
}
