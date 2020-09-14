package com.network.management.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动引导类
 *
 * @author yusheng
 * @date 2020-09-07
 */
@SpringBootApplication(scanBasePackages = {"com.network.management"})
@EnableScheduling
@MapperScan(basePackages = {"com.network.management.mapper"})
public class NetworkManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkManagementApplication.class, args);
    }

}
