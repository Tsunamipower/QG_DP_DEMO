package com.jasonchow.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jason.jwt")
@Data
public class JwtProperties {


    private String SecretKey; ///// 密钥
    private long Ttl; //// 有效时间
    private String TokenName; //// 请求头参数名
}
