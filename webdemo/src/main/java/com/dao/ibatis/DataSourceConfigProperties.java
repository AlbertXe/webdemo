package com.dao.ibatis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(DataSourceConfigProperties.class)
@ConfigurationProperties(prefix = "spring.datasource", ignoreUnknownFields = true)
@Configuration
@Getter
@Setter
@Slf4j
@ToString
public class DataSourceConfigProperties {
    private String mybatisMapper = "classpath*:com/**/*Mapper*.xml";
    private boolean showSql = true;
    private String dialect = "";
    private String interceptors;
    private String typeHandlers;

}
