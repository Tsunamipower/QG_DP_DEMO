package com.jasonchow.config;

import com.jasonchow.interceptor.JwtTokenUserInterceptor;
import com.jasonchow.json.JacksonObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 注册自定义拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/registry");
    }

    /**
     * 生成接口文档
     * @return
     */
    @Bean
    public OpenAPI springOpenAPI() {
        log.info("准备生成接口文档......");
        return new OpenAPI().info(new Info()
                .title("大创项目接口文档")
                .description("大创项目接口文档")
                .version("1.0")
                .contact(new io.swagger.v3.oas.models.info.Contact().name("Jason").email("dev@example.com")));
    }

    @Bean
    public GroupedOpenApi springGroupedOpenApi_1() {
        log.info("准备生成接口文档......");
        return GroupedOpenApi.builder()
                .group("用户后台管理")
                .packagesToScan("com.jasonchow.controller")
                .build();
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射......");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 重写 SpringMVC消息转化器 ，统一对 localtime数据进行格式化处理
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("拓展消息转换器......");
        ///// 创建转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ///// 添加对象转换器
        converter.setObjectMapper(new JacksonObjectMapper());
        converters.add(1,converter); // 这里加Index是因为，转换器有优先级之分，注意，别插入到 0，不然接口文档显示不了。
    }
}
