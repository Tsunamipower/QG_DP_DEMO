package com.jasonchow.constant;

//// 定义异常信息
public class MessageConstant {

    /// 登录接口功能异常
    public static final String ACCOUNT_NOT_FOUND = "该账号不存在";
    public static final String PASSWORD_ERROR = "密码错误";

    /// 注册异常
    public static final String ACCOUNT_USED = "该账号已经存在";

    /// jwt校验异常
    public static final String LOGIN_OUT_DATE = "该账号登录状态已过期，请重新登录";
}
