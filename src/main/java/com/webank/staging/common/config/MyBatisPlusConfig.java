package com.webank.staging.common.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * mybatis plus config
 *
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 配置mybatis-plus 分页查件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
    
    @Bean(name = "base")
    @ConfigurationProperties(prefix = "spring.datasource.druid.base")
    public DataSource base() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(name = "AA0")
    @ConfigurationProperties(prefix = "spring.datasource.druid.AA0")
    public DataSource aa0() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(name = "D00")
    @ConfigurationProperties(prefix = "spring.datasource.druid.D00")
    public DataSource d00() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("base") DataSource base,
                                         @Qualifier("AA0") DataSource aa0,
                                         @Qualifier("D00") DataSource d00) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.base.getValue(), base);
        targetDataSources.put(DBTypeEnum.AA0.getValue(), aa0);
        targetDataSources.put(DBTypeEnum.D00.getValue(), d00);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(base); // 程序默认数据源，这个要根据程序调用数据源频次，经常把常调用的数据源作为默认
        return dynamicDataSource;
    }
    
    
}