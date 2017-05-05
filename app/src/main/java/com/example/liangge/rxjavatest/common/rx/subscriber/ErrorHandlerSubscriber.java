package com.example.liangge.rxjavatest.common.rx.subscriber;

import android.content.Context;
import android.util.Log;

import com.example.liangge.rxjavatest.common.exception.BaseException;
import com.example.liangge.rxjavatest.common.rx.RxErrorHandler;


/**
 * 类名称：ErrorHandlerSubscriber
 * 类描述：(用一句话描述该类做什么)
 * 创建人：ghl
 * 创建时间：2017/5/2 14:40
 * 修改人：ghl
 * 修改时间：2017/5/2 14:40
 *
 * @version v1.0
 */

public abstract class ErrorHandlerSubscriber<T> extends DefaultSubscriber<T> {
    protected RxErrorHandler mErrorHandler = null;

    protected Context mContext;

    public ErrorHandlerSubscriber(Context context) {

        this.mContext = context;


        mErrorHandler = new RxErrorHandler(mContext);

    }

    @Override
    public void onError(Throwable t) {
        BaseException baseException = mErrorHandler.handleError(t);

        if (baseException == null) {
            t.printStackTrace();
            Log.d("ErrorHandlerSubscriber", t.getMessage());
        } else {
            mErrorHandler.showErrorMessage(baseException);
        }
    }

}
