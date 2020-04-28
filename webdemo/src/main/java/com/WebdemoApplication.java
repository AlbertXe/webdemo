package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.dao")
@EnableConfigurationProperties
//@EnableDiscoveryClient
public class WebdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebdemoApplication.class, args);
    }

}
