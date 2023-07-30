package com.sriwin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class TargetDataSourceConfig {

    @ConfigurationProperties(prefix = "spring.target-db.datasource")
    @Bean(name = "targetDataSource")
    public DataSource targetDataSource() throws Exception {
        DataSource dataSource = DataSourceBuilder.create().build();
        dataSource.setLoginTimeout(300);
        return dataSource;
    }

    @Bean
    public DataSource targetCustomDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName(dbTargetDriver);
        //dataSource.setUsername(dbTargetUsername);
        //dataSource.setPassword(dbTargetPassword);
        //dataSource.setUrl(dbTargetUrl);
        return dataSource;
    }
}