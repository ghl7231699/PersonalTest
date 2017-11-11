package com.example.liangge.rxjavatest.ndk.contract;

/**
 * 类名称：BaseNdkView
 * 类描述：所有的页面都实现的公用的方法（比如加载中，加载异常等）
 * 创建人：ghl
 * 创建时间：2017/11/10 下午2:44
 * 修改人：ghl
 * 修改时间：2017/11/10 下午2:44
 *
 * @version v1.0
 */

public interface BaseNdkView {
    /**
     * 错误提示
     */
    void showError();

    /**
     * 加载提示
     */
    void showLoading();

    /**
     *
     */
}
