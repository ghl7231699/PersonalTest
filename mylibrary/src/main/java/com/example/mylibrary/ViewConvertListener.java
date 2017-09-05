package com.example.mylibrary;

import java.io.Serializable;

/**
 * Created by guhongliang on 2017/9/5.
 * NiceDialog子view的点击事件监听处理接口
 */

public interface ViewConvertListener extends Serializable {
    long serilaVersionUID = System.currentTimeMillis();

    void convertView(ViewHolder viewHolder, BaseNiceDialog dialog);
}
