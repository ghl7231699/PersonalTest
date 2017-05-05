package com.example.liangge.rxjavatest.common.rx;

import com.example.liangge.rxjavatest.bean.BaseBean;
import com.example.liangge.rxjavatest.common.exception.ApiException;

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
 * 类名称：RxHttpTransFormer
 * 类描述：过滤
 * 创建人：ghl
 * 创建时间：2017/5/5 16:37
 * 修改人：ghl
 * 修改时间：2017/5/5 16:37
 *
 * @version v1.0
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
                            return Observable.error(new ApiException(Integer.valueOf(tBaseBean.getHeader().getResponse_code()
                            ), tBaseBean.getHeader().getResponse_msg().getDefault_msg()));
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
