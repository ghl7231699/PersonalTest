package com.example.mylibrary;

import java.io.Serializable;

/**
 * Created by guhongliang on 2017/9/5.
 * NiceDialog子view的点击事件监听处理接口
 */

public interface ViewConvertListener extends Serializable {
    long serilaVersionUID = System.currentTimeMillis();

    /**
     * 进行子view的初始化及点击事件
     *
     * @param viewHolder
     * @param dialog
     */
    void convertView(ViewHolder viewHolder, BaseNiceDialog dialog);
}
