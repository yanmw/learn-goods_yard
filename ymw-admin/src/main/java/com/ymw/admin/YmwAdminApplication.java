package com.ymw.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动器
 * @author Louis
 * @date Oct 29, 2018
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.ymw"})
public class YmwAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(YmwAdminApplication.class, args);
	}
}
