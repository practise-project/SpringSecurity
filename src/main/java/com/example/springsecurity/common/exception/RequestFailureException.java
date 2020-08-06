package com.example.springsecurity.common.exception;

/**
 * 请求失败的异常
 * @author TanChong
 * create date 2020\3\2 0002
 */
public class RequestFailureException extends GlobalException {

    public RequestFailureException(String cause) {
        super(cause);
    }
}
