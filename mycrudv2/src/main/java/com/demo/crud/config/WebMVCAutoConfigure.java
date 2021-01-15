package com.demo.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Author: dyhu
 * @Date: 2019/6/17 12:15
 * 视图解析器
 * 目的是自定义解析html入口，如果入口仅仅是一个简单路由或者一个转发，不需要在controller定义方法
 */
@Configuration
public class WebMVCAutoConfigure implements WebMvcConfigurer {
    //必要！
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置thymeleaf的解析器
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    //必要！定义statics可以扫描的路径，否则系统无法访问该目录404
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("index.html").setViewName("login");
        registry.addViewController("index").setViewName("login");
        registry.addViewController("login").setViewName("login");

        registry.addViewController("myvalid").setViewName("myvalid");
        registry.addViewController("validator").setViewName("validator");

    }

}
