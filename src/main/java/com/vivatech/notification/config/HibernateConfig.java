package com.vivatech.notification.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public ImplicitNamingStrategyJpaCompliantImpl implicitNamingStrategy() {
        return new ImplicitNamingStrategyJpaCompliantImpl();
    }

    @Bean
    public PhysicalNamingStrategyStandardImpl physicalNamingStrategy() {
        return new PhysicalNamingStrategyStandardImpl();
    }
}
