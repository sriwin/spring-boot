package com.sriwin.config;

import com.sriwin.constants.AppConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
public class JPABeansConfig {
    //@Bean("sourceEntityManagerFactory")
    //@Primary
    public LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        //factoryBean.setPackagesToScan(DBConstants.SOURCE_ENTITY_PACKAGE);
        //factoryBean.setJpaVendorAdapter(sourceJPAVendorAdapter());
        factoryBean.setJpaProperties(new Properties());
        //factoryBean.setDataSource(sourceDataSource());
        return factoryBean;
    }

    //@Bean
    //@Primary
    public JpaVendorAdapter sourceJPAVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
        jpaVendorAdapter.setDatabase(Database.ORACLE);
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setShowSql(false);

        return jpaVendorAdapter;
    }

    private Map<String, Object> hibernateProperties() {
        Resource resource = new ClassPathResource(AppConstants.ORACLE_HIBERNATE_PROPERTIES);
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().toString(),
                            Map.Entry::getValue)
                    );
        } catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }
}