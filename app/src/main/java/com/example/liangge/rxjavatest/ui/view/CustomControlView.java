package com.example.liangge.rxjavatest.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;

/**
 * Created by guhongliang on 2017/8/7.
 * 自定义组合控件
 */

public class CustomControlView extends RelativeLayout {
    private Button titleBarLeftBtn, titleBarRightBtn;
    private TextView titleBarTitle;

    public Button getTitleBarLeftBtn() {
        return titleBarLeftBtn;
    }

    public Button getTitleBarRightBtn() {
        return titleBarRightBtn;
    }

    public TextView getTitleBarTitle() {
        return titleBarTitle;
    }

    public CustomControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_control_view_layout, this, true);
        titleBarLeftBtn = (Button) findViewById(R.id.title_bar_left);
        titleBarRightBtn = (Button) findViewById(R.id.title_bar_right);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomControlView);
        if (attributes != null) {
            handlerLeftBtn(attributes);
            handlerTextView(attributes);
            handlerRightBtn(attributes);
        }
        attributes.recycle();
    }

    /**
     * 处理右侧按钮
     *
     * @param attributes
     */
    private void handlerRightBtn(TypedArray attributes) {
        //获取是否显示右侧按钮
        boolean aBoolean = attributes.getBoolean(R.styleable.CustomControlView_right_button_visible, true);
        if (aBoolean) {
            titleBarRightBtn.setVisibility(VISIBLE);
        } else
            titleBarRightBtn.setVisibility(INVISIBLE);
        //设置右侧按钮文字
        String leftText = attributes.getString(R.styleable.CustomControlView_right_button_text);
        if (!TextUtils.isEmpty(leftText)) {
            titleBarRightBtn.setText(leftText);
            //设置右侧按钮文字颜色
            int color = attributes.getResourceId(R.styleable.CustomControlView_right_button_text_color, Color.WHITE);
            titleBarRightBtn.setTextColor(color);
        } else
        //设置左边图片icon，只能二选一，要么只能是文字，要么只能是图片
        {
            int rightDra = attributes.getResourceId(R.styleable.CustomControlView_right_button_text_drawable, -1);
            if (rightDra != -1) {
                titleBarRightBtn.setBackgroundResource(rightDra);
            }
        }

    }

    /**
     * 处理文本标题
     *
     * @param attributes
     */
    private void handlerTextView(TypedArray attributes) {
        //先获取标题是否显示图片icon
        int textDra = attributes.getResourceId(R.styleable.CustomControlView_title_text_drawable, -1);
        if (textDra != -1) {
            titleBarTitle.setBackgroundResource(textDra);
        } else {
            //如果不是图片标题，则获取文字标题
            String text = attributes.getString(R.styleable.CustomControlView_title_text);
            if (!TextUtils.isEmpty(text)) {
                titleBarTitle.setText(text);
            }
            //获取标题显示颜色
            int color = attributes.getResourceId(R.styleable.CustomControlView_title_text_color, Color.WHITE);
            titleBarTitle.setTextColor(color);
        }
    }

    /**
     * 处理左侧按钮
     *
     * @param attributes
     */
    private void handlerLeftBtn(TypedArray attributes) {
        //处理titleBar背景色
        int titleBarBackGround = attributes.getResourceId(R.styleable.CustomControlView_title_background_color
                , Color.GREEN);
        setBackgroundResource(titleBarBackGround);
        //先处理左边按钮，获取是否要显示左边按钮
        boolean leftButtonVisible = attributes.getBoolean(R.styleable.CustomControlView_left_button_visible
                , true);
        if (leftButtonVisible) {
            titleBarLeftBtn.setVisibility(VISIBLE);
        } else {
            titleBarLeftBtn.setVisibility(INVISIBLE);
        }
        //设置左边按钮的文字
        String leftButtonText = attributes.getString(R.styleable.CustomControlView_left_button_text);
        if (!TextUtils.isEmpty(leftButtonText)) {
            titleBarLeftBtn.setText(leftButtonText);
            //设置左边按钮的文字颜色
            int leftBtnColor = attributes.getResourceId(R.styleable.CustomControlView_left_button_text_color, Color.WHITE);
            titleBarLeftBtn.setTextColor(leftBtnColor);
        } else {
            //设置左边图片icon，只能二选一，要么只能是文字，要么只能是图片
            int leftBtnDra = attributes.getResourceId(R.styleable.CustomControlView_left_button_text_drawable, R.mipmap.backup);
            if (leftBtnDra != -1) {
                titleBarLeftBtn.setBackgroundResource(leftBtnDra);
            }
        }
    }

    public void setTitleClickListener(OnClickListener listener) {
        if (listener != null) {
            titleBarLeftBtn.setOnClickListener(listener);
            titleBarRightBtn.setOnClickListener(listener);
        }
    }
}
