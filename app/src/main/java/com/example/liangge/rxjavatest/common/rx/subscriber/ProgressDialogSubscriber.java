package com.example.liangge.rxjavatest.common.rx.subscriber;

import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * Created by guhongliang on 2017/5/2.
 */

public abstract class ProgressDialogSubscriber<T> extends ErrorHandlerSubscriber<T> implements ProgressDialogHandler.OnProgressCancelListener {
    private ProgressDialogHandler mProgressDialogHandler;

    public ProgressDialogSubscriber(Context context) {
        super(context);
        mProgressDialogHandler = new ProgressDialogHandler(mContext);
    }

    protected boolean isShowProgressDialog() {
        return true;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

        if (isShowProgressDialog()) {
            this.mProgressDialogHandler.showProgressDialog();
        }
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
        if (isShowProgressDialog()) {
            this.mProgressDialogHandler.dismissProgressDialog();
        }
    }

    @Override
    public void onComplete() {
        if (isShowProgressDialog()) {
            this.mProgressDialogHandler.dismissProgressDialog();
        }
    }
}
