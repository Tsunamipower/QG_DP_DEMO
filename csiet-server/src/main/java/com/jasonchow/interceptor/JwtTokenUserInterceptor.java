package com.jasonchow.interceptor;

import com.jasonchow.constant.JwtClaimsConstant;
import com.jasonchow.constant.MessageConstant;
import com.jasonchow.content.BaseContext;
import com.jasonchow.properties.JwtProperties;
import com.jasonchow.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.LoginException;

/**
 *  jwt 拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("jwt校验启动......");

        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        ///// 获取token
        String token = request.getHeader(jwtProperties.getTokenName());
        try {
            /// 解析token
            /// 获取声明部分，如果token为空，里面解析时会报异常
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            /// 获取声明部分中的用户id
            Long UserId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id：{}",UserId);
            //// 如果用户id为空，返回异常
            if (UserId == null) {
                throw new LoginException(MessageConstant.LOGIN_OUT_DATE);
            }
            //// 把用户id存到线程空间里面
            BaseContext.setCurrentId(UserId);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
