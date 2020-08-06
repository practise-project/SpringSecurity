package com.example.springsecurity.common.exception;

/**
 * @author TanChong
 * create date 2020\3\2 0002
 */
public class SystemErrorException extends GlobalException {

    public SystemErrorException(String message) {
        super(message);
    }

    public SystemErrorException(int errorCode, String message) {
        super(errorCode, message);
    }
}
