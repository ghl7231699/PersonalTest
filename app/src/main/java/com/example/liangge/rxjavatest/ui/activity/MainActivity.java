package com.example.liangge.rxjavatest.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.liangge.rxjavatest.IMyAidlInterface;
import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.aidl.TestService;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private TextView tv;
    IMyAidlInterface mStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.main_text);
    }

    private void initData() {
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mStub = IMyAidlInterface.Stub.asInterface(service);
            if (mStub == null) {
                return;
            } else {
                try {
                    mStub.add(1, "this is my first aidl file");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void onclick(View view) {
//        Observable<String> observable = getObservable();
////        Observer observer=getObserver();
////        observable.subscribe(observer);
//        observable.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                Log.d(TAG, "accept: " + s);
//                tv.append(s);
//                tv.append(".......");
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(@NonNull Throwable throwable) throws Exception {
//
//            }
//        });

        initData();
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
