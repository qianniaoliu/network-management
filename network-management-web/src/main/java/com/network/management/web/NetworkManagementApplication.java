package com.network.management.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动引导类
 *
 * @author yusheng
 */
@SpringBootApplication(scanBasePackages = {"com.network.management"})
@EnableScheduling
@MapperScan(basePackages = {"com.network.management.mapper"})
@EnableSwagger2
public class NetworkManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkManagementApplication.class, args);
    }

}
