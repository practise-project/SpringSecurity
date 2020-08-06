package com.example.springsecurity.common.exception;

/**
 * 无效参数异常。
 *
 * @author TanChong
 * create date 2020\3\2 0002
 */
public class ValueInvalidException extends GlobalException {

    public ValueInvalidException(String message) {
        super(message);
    }
}
