package com.jasonchow.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    //// 扫描mapper文件夹所有接口中，带有自定义注解标识的方法
    @Pointcut("execution(* com.jasonchow.mapper.*.*(..)) && @annotation(com.jasonchow.annotation.AutoFill)")
    public void pointcut_1() {}

    /**
     * 前置通知，为公共字段赋值
     * @param joinPoint
     */
    // TODO：好像暂时用不上
    @Before("pointcut_1()")
    public void HandleAutoFill(JoinPoint joinPoint) {
        //log.info("开始公共字段填充");

    }
}
