package com.ymw.admin.config;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis配置
 * @author Louis
 * @date Oct 29, 2018
 */

@EnableTransactionManagement
@Configuration
@MapperScan("com.ymw.*.dao")	// 扫描DAO
public class MybatisConfig {
    /**
     * mybatis plus 分页插件
     * @return
     */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor().setDialectType("mysql");
  }
}