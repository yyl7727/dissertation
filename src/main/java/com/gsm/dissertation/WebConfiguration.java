package com.gsm.dissertation;

import com.gsm.dissertation.util.CheckUserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public CheckUserInterceptor checkUserInterceptor(){
        return new CheckUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkUserInterceptor());
        //WebMvcConfigurer.super.addInterceptors(registry);
    }
}
