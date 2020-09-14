package com.network.management.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yusheng
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 10);
        return commonsMultipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = System.getProperty("user.dir");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + rootPath + "/static/img/");
    }
}
