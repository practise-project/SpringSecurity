package com.example.springsecurity.common.exception;

/**
 * 全局异常基类。
 * @author TanChong
 * create date 2020\3\2 0002
 */
public class GlobalException extends Exception{
    /**
     * 错误码。
     */
    private int errorCode;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(int errorCode, String message) {
        super(message);
        this.errorCode  = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
