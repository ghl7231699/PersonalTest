package com.example.liangge.rxjavatest.ui.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.inter.PermissonListener;
import com.example.liangge.rxjavatest.common.utils.PermissionUtil;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AuthCodeActivity extends BaseActivity {
    @BindView(R.id.auto_code_edit_phone)
    EditText mAutoCodeEditPhone;
    @BindView(R.id.auto_code_edit_content)
    EditText mAutoCodeEditContent;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.password_content_edit)
    EditText mPwdEdit;
    @BindView(R.id.password_content_img)
    ImageView mPwdImg;

    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth_code;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

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
                        mBtnSend.setEnabled(false);
                        mBtnSend.setBackgroundResource(R.drawable.edit_text_boder_send);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mBtnSend.setTextColor(AuthCodeActivity.this.getResources().getColor(R.color.colorLight, null));
                        }
                        mBtnSend.setText("剩余" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mBtnSend.setEnabled(true);
                        mBtnSend.setBackgroundResource(R.drawable.edit_text_boder);
                        mBtnSend.setTextColor(Color.BLACK);
                        mBtnSend.setText("发送验证码");
                    }
                });
    }

    @OnClick(R.id.password_content_img)
    public void onViewClicked() {
        if (!isVisible) {
            mPwdImg.setBackgroundResource(R.mipmap.password_visible);
            mPwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            PermissionUtil.requestPermission(this, new PermissonListener() {
                        @Override
                        public void onGranted() {

                        }

                        @Override
                        public void onDenied() {

                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE
            );
            isVisible = true;
        } else {
            mPwdImg.setBackgroundResource(R.mipmap.password_invisible);
            mPwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            isVisible = false;
        }
    }
}
