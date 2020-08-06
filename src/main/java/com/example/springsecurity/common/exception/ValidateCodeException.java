package com.example.springsecurity.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 图形验证码异常处理
 * @author TanChong
 * create date 2020\3\3 0003
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long SERIAL_VERSION_UID = 1L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
