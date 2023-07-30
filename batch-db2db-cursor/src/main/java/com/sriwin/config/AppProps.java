package com.sriwin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProps {
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.servlet.context-path}")
    private String contextPath;
}
