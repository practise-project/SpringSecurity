package com.example.springsecurity.common.exception;

/**
 * 无效操作异常
 * @author tanchong
 * Create Date: 2020/3/16
 */
public class InvalidOperationException extends GlobalException{
    public InvalidOperationException(String message) {
        super(message);
    }
}
