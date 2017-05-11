package com.example.liangge.rxjavatest.data;

import android.util.Log;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.BaseBean;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.bean.UserInfo;
import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.data.http.Api;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.example.liangge.rxjavatest.R.mipmap.f;

/**
 * Created by guhongliang on 2017/5/8.
 */

public class RxDownListModule {

    public static Fruit[] mFruits = {
            new Fruit("展业区", f, 1, 1, HttpUrls.URL_1),
            new Fruit("腾讯视频", R.mipmap.g, 2, 1, HttpUrls.URL_2),
            new Fruit("酷狗", R.mipmap.h, 3, 1, HttpUrls.URL_3),
            new Fruit("网易云音乐", R.mipmap.j, 1, 1, HttpUrls.URL_4),
            new Fruit("爱奇艺视频", R.mipmap.k, 2, 1, HttpUrls.URL_5),
            new Fruit("西游降魔篇", R.mipmap.l, 3, 1, HttpUrls.URL_6),
    };
    Api mApi;

    public RxDownListModule(Api api) {
        mApi = api;
    }

    public Observable<BaseBean<UserInfo>> loadDownList(UserParam p) {
        return mApi.userLogin(p);
    }

    public Observable<List<Fruit>> getData() {
        return Observable.create(new ObservableOnSubscribe<List<Fruit>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Fruit>> e) throws Exception {
                List<Fruit> all = DataSupport.findAll(Fruit.class);
                List<Fruit> list = new ArrayList<>();
                if (all.size()==0) {
                    for (int i = 0; i < mFruits.length; i++) {
                        Fruit fruit = mFruits[i];
                        Log.d(TAG, "getDatas: " + fruit.getName());
                        fruit.save();
                        list.add(fruit);
                    }
                    e.onNext(list);
                    e.onComplete();
                } else {
                    e.onNext(all);
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
