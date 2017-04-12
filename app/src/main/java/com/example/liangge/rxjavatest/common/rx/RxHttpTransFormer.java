package com.example.liangge.rxjavatest.common.rx;

import com.example.liangge.rxjavatest.bean.BaseBean;
import com.example.liangge.rxjavatest.common.exception.APiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liangge on 2017/4/12.
 */

public class RxHttpTransFormer {
    public static <T> ObservableTransformer<BaseBean<T>, T> handleResult() {
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
                return upstream.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final BaseBean<T> tBaseBean) throws Exception {
                        if (tBaseBean.getHeader().success()) {
                            return obtainResult(tBaseBean);
                        } else {
                            return Observable.error(new APiException(tBaseBean.getHeader().getResponse_code()
                                    ,tBaseBean.getHeader().getResponse_msg().getDefault_msg()));
                        }
//                        return Observable.empty();
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }

            private ObservableSource<T> obtainResult(@NonNull final BaseBean<T> tBaseBean) {
                return Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> e) throws Exception {
                        try {
                            e.onNext(tBaseBean.getData());
                            e.onComplete();
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        };
    }
}
