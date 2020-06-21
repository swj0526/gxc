package com.swj.config;



import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author 孙文举
 * @description
 * @create 2020-05-29 19:56
 */
@Configuration
@MapperScan("com.swj.mapper")
@ComponentScan(basePackages = {"com.swj"})
public class MyBatisPlusConfig {

/*    *//**
     * 逻辑删除插件
     *//*
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }*/
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}