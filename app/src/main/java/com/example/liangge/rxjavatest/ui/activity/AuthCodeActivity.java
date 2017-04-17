package com.example.liangge.rxjavatest.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liangge.rxjavatest.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AuthCodeActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_code);
        btn = (Button) findViewById(R.id.btn_send);
    }

    public void onclick(View view) {
        final int count = 10;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        btn.setEnabled(false);
                        btn.setBackgroundResource(R.drawable.edit_text_boder_send);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            btn.setTextColor(AuthCodeActivity.this.getResources().getColor(R.color.colorLight,null));
                        }
                        btn.setText("剩余" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        btn.setEnabled(true);
                        btn.setBackgroundResource(R.drawable.edit_text_boder);
                        btn.setTextColor(Color.BLACK);
                        btn.setText("发送验证码");
                    }
                });
    }
}
