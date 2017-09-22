package com.example.bargraph.watcher;

/**
 * Created by guhongliang on 2017/9/22.
 * 抽象的被观察者
 */

public interface Watchered {
    /**
     * 添加观察者
     *
     * @param watcher
     */
    void addWatcher(Watcher watcher);

    /**
     * 移除观察者
     *
     * @param watcher
     */
    void removeWatcher(Watcher watcher);

    /**
     * 通知观察者状态改变
     */
    void notifyChanged();
}
