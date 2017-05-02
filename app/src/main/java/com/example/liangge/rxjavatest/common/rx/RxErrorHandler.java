package com.example.liangge.rxjavatest.common.rx;

import android.content.Context;
import android.widget.Toast;

import com.example.liangge.rxjavatest.common.exception.ApiException;
import com.example.liangge.rxjavatest.common.exception.BaseException;
import com.example.liangge.rxjavatest.common.exception.ErrorMessageFactory;
import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * 类名称：RxErrorHandler
 * 类描述：
 * 创建人：ghl
 * 创建时间：2017/5/2 14:21
 * 修改人：ghl
 * 修改时间：2017/5/2 14:21
 *
 * @version v1.0
 */

public class RxErrorHandler {


    private Context mContext;

    public RxErrorHandler(Context context) {

        this.mContext = context;
    }

    public BaseException handleError(Throwable e) {

        BaseException exception = new BaseException();

        if (e instanceof ApiException) {

            exception.setCode(((ApiException) e).getCode());

        } else if (e instanceof JsonParseException) {
            exception.setCode(BaseException.JSON_ERROR);
        } else if (e instanceof HttpException) {

            exception.setCode(((HttpException) e).code());
        } else if (e instanceof SocketTimeoutException) {

            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        } else if (e instanceof SocketException) {


        } else {

            exception.setCode(BaseException.UNKNOWN_ERROR);

        }

        exception.setDisplayMessage(ErrorMessageFactory.create(mContext, exception.getCode()));

        return exception;
    }


    public void showErrorMessage(BaseException e) {


        Toast.makeText(mContext, e.getDisplayMessage(), Toast.LENGTH_LONG).show();

    }
}
