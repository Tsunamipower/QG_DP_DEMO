package com.jasonchow.handler;

import com.jasonchow.exception.BaseException;
import com.jasonchow.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，统一处理所有异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
       log.info("异常消息：{}", ex.getMessage());
       return Result.error(ex.getMessage());
    }
}
