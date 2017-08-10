package com.example.liangge.rxjavatest.common.utils;

import com.example.liangge.rxjavatest.common.constant.Data;
import com.example.liangge.rxjavatest.common.constant.Header;
import com.example.liangge.rxjavatest.common.constant.UserParam;

/**
 * Created by guhongliang on 2017/8/9.
 */

public class CommonUtils {
    /**
     * 请求参数
     *
     * @return
     */
    public static UserParam getUserParam() {
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "ebt-003");
        Data data = new Data("19680106", "17205261");
        UserParam param = new UserParam();
        param.setHeader(header);
        param.setUserData(data);
        return param;
    }
}
