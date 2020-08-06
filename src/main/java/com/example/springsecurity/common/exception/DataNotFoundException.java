package com.example.springsecurity.common.exception;

/**
 * 数据未找到异常。
 * @author TanChong
 * create date 2020\3\2 0002
 */
public class DataNotFoundException extends GlobalException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(int code, String message) {
        super(code, message);
    }
}
