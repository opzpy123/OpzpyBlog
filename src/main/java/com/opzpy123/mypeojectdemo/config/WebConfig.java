package com.opzpy123.mypeojectdemo.config;

import com.opzpy123.mypeojectdemo.interceptor.DeleteInteceptor;
import com.opzpy123.mypeojectdemo.interceptor.PublishEditInterceptor;
import com.opzpy123.mypeojectdemo.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private PublishEditInterceptor publishEditInterceptor;

    @Autowired
    private DeleteInteceptor deleteInteceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
        registry.addInterceptor(publishEditInterceptor).addPathPatterns("/publish/*");
        registry.addInterceptor(deleteInteceptor).addPathPatterns("/delete/*");
    }


 }
