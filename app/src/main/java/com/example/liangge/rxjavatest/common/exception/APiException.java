package com.example.liangge.rxjavatest.common.exception;

/**
 * Created by liangge on 2017/4/12.
 */

public class APiException extends BaseException {
    public APiException(String code, String displayMessage) {
        super(code, displayMessage);
    }
}
