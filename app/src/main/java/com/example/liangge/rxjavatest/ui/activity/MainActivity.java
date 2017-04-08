package com.example.liangge.rxjavatest.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.main_text);
    }

    public void onclick(View view) {
        Observable<String> observable = getObservable();
//        Observer observer=getObserver();
//        observable.subscribe(observer);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "accept: " + s);
                tv.append(s);
                tv.append(".......");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
    }

    private Observable<String> getObservable() {
//        return Observable.create(new ObservableOnSubscribe() {
//            @Override
//            public void subscribe(ObservableEmitter e) throws Exception {
//                e.onNext("大保健");
//                e.onNext("泡吧");
//                e.onComplete();
//            }
//        });
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "大保健";
            }
        });
//        return  Observable.just("大保健","泡吧","撩妹");
    }

    /**
     * 获取observer
     *
     * @return
     */
    public Observer<String> getObserver() {

        return new Observer<String>() {
            Disposable dd = null;

            @Override
            public void onSubscribe(Disposable d) {
                dd = d;
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                if (s.equals("泡吧")) {
                    dd.dispose();
                }
                Log.d(TAG, "onNext: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }
}
