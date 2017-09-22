package com.example.bargraph.watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhongliang on 2017/9/22.
 */

public class Food implements Watchered {
    private List<Watcher> mWatchers = new ArrayList<>();

    private String price;
    private int num;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void addWatcher(Watcher watcher) {
        mWatchers.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        if (mWatchers.isEmpty()) {
            return;
        }
        mWatchers.remove(watcher);
    }

    @Override
    public void notifyChanged() {
        for (Watcher w : mWatchers
                ) {
            w.update(this);
        }
    }
}
