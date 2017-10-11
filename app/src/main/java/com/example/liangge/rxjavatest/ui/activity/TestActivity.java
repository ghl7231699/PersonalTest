package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.mylibrary.DLog;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

    private static String TAG = TestActivity.class.getSimpleName();
    @BindView(R.id.content_test)
    TextView mContentTest;
    @BindView(R.id.et_test)
    EditText mEtTest;
    private List<Integer> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_create, R.id.search_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
//                createRxJava();
//                map();
//                flatMap();
                distinct();
                filter();
                break;
            case R.id.search_test:
                deBounce(mEtTest);
                break;
            default:
                break;
        }
    }


    /**
     * 由于1.x中Observable不能合理的背压，导致了无法意料的 MissingBackpressureException，
     * 所以在2.x中，添加了Flowable来支持背压，而把Observable设计成非背压的
     */
    private void createRxJava() {
        Observable.just("1").subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mContentTest.append(s);
            }
        });
        //创建观察者或订阅者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //这个方法可以用于解除订阅
                if (d.isDisposed()) {
                    d.dispose();
                }
                DLog.e(TAG, "onSubscribe 执行了");
            }

            @Override
            public void onNext(String s) {
                //为观察者提供一个新项目来观察，这个方法会被调用多次
                DLog.e(TAG, "onNext 执行了" + s);
            }

            @Override
            public void onError(Throwable e) {
                //发生错误，此方法中进行处理
                //如果调用了这个方法 则就不会调用onNext和onComplete
                DLog.e(TAG, "onError 执行了");
            }

            @Override
            public void onComplete() {
                //通知观察者，这个方法同onError的关系是互斥，及如果执行了onError 则不执行onComplete
                DLog.e(TAG, "onComplete 执行了");
            }
        };
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //被观察者发射器，通过调用观察者或订阅者的方法进行数据的传递
                if (0 < 100) {
                    e.onError(new Throwable());
                    return;
                }
                e.onNext("Hello  World");
                e.onComplete();
            }
        });
        //订阅
        observable.subscribe(observer);
        /**
         * Flowable创建方式  支持背压
         *
         * 在2.x中，我们在onSubscribe()回调中必须调用s.request()方法去请求资源，参数就是要请求的数量，一般如果不限制请求数量，
         * 可以写成Long.MAX_VALUE，之后会立即触发onNext()方法！所以当你在onSubscribe()/onStart()中做了一些初始化的工作，
         * 而这些工作是在request()后面时，会出现一些问题，在onNext()执行时，你的初始化工作的那部分代码还没有执行。
         * 为了避免这种情况，请确保你调用request()时，已经把所有初始化工作做完了
         */
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("I am China");
            }
        }, BackpressureStrategy.BUFFER).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                //这一步是必须操作的，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
                //调用request()方法，会立即触发onNext()方法
                //在onComplete()方法完成，才会再执行request()后边的代码
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                DLog.e(TAG, "onNext 执行了" + s);
            }

            @Override
            public void onError(Throwable t) {
                DLog.e(TAG, "onError 执行了");
            }

            @Override
            public void onComplete() {
                DLog.e(TAG, "onComplete 执行了");
            }
        });
    }

    /**
     * 作者：ghl
     * 描述：map操作符
     * 创建时间：2017/10/9 下午3:43
     *
     * @Params:
     * @Return:
     */
    private void map() {
//        Observable.just("Map 操作符")
//                .map(new Function<String, Fruit>() {
//                    @Override
//                    public Fruit apply(@NonNull String s) throws Exception {
//                        Fruit fruit = new Fruit();
//                        fruit.setName(s);
//                        return fruit;
//                    }
//                })
//                .subscribe(new Consumer<Fruit>() {
//                    @Override
//                    public void accept(@NonNull Fruit fruit) throws Exception {
//                        mContentTest.setText(fruit.getName());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        //操作过程中出现异常，则可以在这个方法中进行处理
//                    }
//                });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                e.onComplete();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(@NonNull String s) throws Exception {
                byte[] bytes = s.getBytes();
                int a = bytes[0];
                return a;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                DLog.e(TAG, "accept 最后的结果为" + integer);
            }
        });

    }

    /**
     * 作者：ghl
     * 描述：flatMap
     * 创建时间：2017/10/9 下午3:43
     *
     * @Params:
     * @Return:
     */
    private void flatMap() {
        for (int i = 0; i < 5; i++) {
            mList.add(i);
        }
        Observable.just(mList)
                .flatMap(new Function<List<Integer>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull List<Integer> integers) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < integers.size(); i++) {
                            list.add(String.valueOf(integers.get(i)));
                        }
                        return Observable.just(list);
                    }
                }).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> strings) throws Exception {
                for (String s :
                        strings) {
                    DLog.e(TAG, s);
                }
            }
        });
    }

    /**
     * 作者：ghl
     * 描述：去重
     * 创建时间：2017/10/9 下午3:52
     *
     * @Params:
     * @Return:
     */
    private void distinct() {
        Observable.just(1, 1, 1, 2, 2, 3, 3, 4, 9, 0)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        DLog.e(TAG, "distinct--->" + integer);
                    }
                });
    }

    /**
     * 作者：ghl
     * 描述：过滤操作
     * 创建时间：2017/10/9 下午3:52
     *
     * @Params:
     * @Return:
     */
    private void filter() {
        Observable.just(1, 10, 11, 3, 40, 50, 41)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer > 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                DLog.e(TAG, "filter--->" + integer);
            }
        });
    }

    private void deBounce(EditText editText) {
        Date date = new Date();
        mContentTest.append("点击时的时间 " + date.getTime() + "\n");
        DLog.e(TAG, "开始搜索" + date.getTime());
        RxTextView
                .textChanges(editText)
                //延迟执行搜索操作
                .debounce(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull CharSequence charSequence) throws Exception {
                        List<String> list = new ArrayList<>();
                        list.add("搜索了");
                        return Observable.just(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        for (String s :
                                strings) {
                            DLog.e(TAG, "deBounce--->" + new Date().getTime());
                            mContentTest.append("延时之后的时间 " + new Date().getTime() + "\n");
                        }
                    }
                });

    }
}
