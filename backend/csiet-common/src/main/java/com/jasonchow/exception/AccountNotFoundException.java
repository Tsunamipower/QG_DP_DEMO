package com.jasonchow.exception;

/**
 * 账号找不到
 */
public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException() {}
    public AccountNotFoundException(String message) {
        super(message);
    }
}
