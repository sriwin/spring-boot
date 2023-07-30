package com.sriwin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class SourceDataSourceConfig {

    @ConfigurationProperties(prefix = "spring.source-db.datasource")
    @Bean(name = "dataSource")
    @Primary
    public DataSource sourceDataSource() throws Exception {
        DataSource dataSource = DataSourceBuilder.create().build();
        dataSource.setLoginTimeout(300);
        return dataSource;
    }

    @Bean
    public DataSource sourceCustomDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName(dbTargetDriver);
        //dataSource.setUsername(dbTargetUsername);
        //dataSource.setPassword(dbTargetPassword);
        //dataSource.setUrl(dbTargetUrl);
        return dataSource;
    }

    /*
    @Bean(name = "xyzEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("")
                .persistenceUnit("fooDb")
                .build();
    }
    */
}