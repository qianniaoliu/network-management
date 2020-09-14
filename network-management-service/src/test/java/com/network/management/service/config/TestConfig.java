package com.network.management.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yusheng
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.network.management"})
@MapperScan(basePackages = "com.network.management.mapper")
public class TestConfig {
}
