package com.atguigu.crud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new BossAuthInterceptor())
                .addPathPatterns("/emps/**") //intercept all request
                .excludePathPatterns("error")
                .excludePathPatterns("/index/**");   //release all request in index file
    }

}
