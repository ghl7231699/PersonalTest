package com.example.liangge.rxjavatest.presenter;

import android.Manifest;
import android.app.Activity;

import com.example.liangge.rxjavatest.bean.UserInfo;
import com.example.liangge.rxjavatest.common.constant.Data;
import com.example.liangge.rxjavatest.common.constant.Header;
import com.example.liangge.rxjavatest.common.constant.UserParam;
import com.example.liangge.rxjavatest.common.rx.RxHttpTransFormer;
import com.example.liangge.rxjavatest.data.UserInfoModule;
import com.example.liangge.rxjavatest.presenter.contract.UserInfoContract;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by guhongliang on 2017/8/2.
 */

public class RxjavaPresenter extends BasePresenter<UserInfoModule, UserInfoContract.RxView> {
    @Inject
    public RxjavaPresenter(UserInfoModule userInfoModule, UserInfoContract.RxView view) {
        super(userInfoModule, view);
    }

    private UserParam getUserParam() {
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "ebt-003");
        Data data = new Data("19680106", "17205261");
        UserParam param = new UserParam();
        param.setHeader(header);
        param.setUserData(data);
        return param;
    }

    /**
     * create
     */
    public void create() {
        mV.showUserInfo(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("亮哥：刘东，你是猪吗" + "\n");
                e.onNext("刘东：我确实是个猪" + "\n");
                e.onComplete();
                e.onNext("亮哥：你个山炮" + "\n");
            }
        }), 1);
    }

    /**
     * map
     */
    public void map() {
        mV.showUserInfo(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }), 2);
    }

    /**
     * flatMap
     */
    public void flatMap() {
        RxPermissions permissions = new RxPermissions((Activity) mV);
        mV.showUserInfo(permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .flatMap(new Function<Boolean, ObservableSource<UserInfo>>() {
                    @Override
                    public ObservableSource<UserInfo> apply(@NonNull Boolean aBoolean) throws Exception {

                        if (aBoolean) {
                            return mM.LoadUserInfo(getUserParam())
                                    .compose(RxHttpTransFormer.<UserInfo>handleResult());
                        } else
                            mV.disMissLoading();
                        mV.showError("拒绝权限，无法进行正常操作");
                        return Observable.empty();
                    }
                }), 3);
// .subscribe(new Consumer<UserInfo>() {
//            @Override
//            public void accept(@NonNull UserInfo userInfo) throws Exception {
//                if (userInfo != null) {
//                    mV.showUserInfo(userInfo, 3);
//                }
//            }
//
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(@NonNull Throwable throwable) throws Exception {
//                mV.showError(throwable.toString());
//            }
//        });
    }

    /**
     * zip 专用于合并事件，该合并不是连接，而是俩俩配对，也就意味着，最终配对的Observable发射事件数目只和少的那个相同
     * zip组合事件的过程就是分别从发射器A和发射器B各取出一个事件来组合，并且一个事件只能被使用一次，组合的顺序是严格按照事件发送的顺序来进行的
     */
    public void zip() {
        mV.showUserInfo(Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String,
                Integer, Object>() {

            @Override
            public Object apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                return s + integer + "\n";
            }
        }), 4);
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    e.onNext("B");
                    e.onNext("C");
                    e.onNext("D");
                }
            }
        });

    }

    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                }
            }
        });
    }

    public void concat() {
        mV.showUserInfo(Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6)), 5);
    }

    /**
     * distinct 去重
     */
    public void distinct() {
        mV.showUserInfo(Observable.just(1, 1, 1, 2, 2, 3, 3, 4, 9, 0).distinct(), 6);
    }

    /**
     * filter过滤
     */
    public void filter() {
        mV.showUserInfo(Observable.just(1, 10, 2, 3, 40, 50, 7).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 10;
            }
        }), 7);
    }
}
