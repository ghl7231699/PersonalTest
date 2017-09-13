package com.example.statisticsclickmodule;

import android.view.View;

/**
 * Created by guhongliang on 2017/9/12.
 */

public interface ClickListener {

    void setOnClick(View view);//

    /**
     * 自定义点击事件
     *
     * @param clickId   自定义事件的id
     * @param clickName 自定义事件的名称
     */
    void setCustomClick(int clickId, String clickName);

}
