package com.example.springsecurity.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author TanChong
 * create date 2020\3\3 0003
 */
public class InvalidLogonParamException extends AuthenticationException {
    public InvalidLogonParamException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidLogonParamException(String msg) {
        super(msg);
    }
}
