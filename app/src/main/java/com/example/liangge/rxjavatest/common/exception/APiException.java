package com.example.liangge.rxjavatest.common.exception;

/**
 * 类名称：ApiException
 * 类描述：Api端exception
 * 创建人：ghl
 * 创建时间：2017/5/2 9:52
 * 修改人：ghl
 * 修改时间：2017/5/2 9:52
 * @version v1.0
 */

public class ApiException extends BaseException {
    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}
