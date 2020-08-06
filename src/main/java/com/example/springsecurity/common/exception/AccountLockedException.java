package com.example.springsecurity.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author tanchong
 * Create Date: 2020/3/6
 */
public class AccountLockedException extends AuthenticationException {
    public AccountLockedException(String message) {
        super(message);
    }
}
