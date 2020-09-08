package com.network.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yusheng
 * @date 2020-09-07
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.network.management.mapper"})
public class NetworkManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkManagementApplication.class, args);
	}

}
