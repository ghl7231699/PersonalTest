package com.example.liangge.rxjavatest.common.constant;

/**
 * 类名称：
 * 类描述：单例
 * 创建人：ghl
 * 创建时间：2017/5/23 9:13
 * 修改人：ghl
 * 修改时间：2017/5/23 9:13
 *
 * @version v1.0
 */

public class SingleInstance {
    private static volatile SingleInstance instance;

    private SingleInstance() {
    }

    public static SingleInstance getInstance() {
        if (instance == null) {
            synchronized (SingleInstance.class) {
                if (instance == null) {
                    instance = new SingleInstance();
                }
            }
        }
        return instance;
    }
}
