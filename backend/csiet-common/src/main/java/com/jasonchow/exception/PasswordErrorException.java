package com.jasonchow.exception;

/**
 * 密码错误
 */
public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {}
    public PasswordErrorException(String message) {
        super(message);
    }
}
