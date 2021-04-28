package ru.beerbis.springer.service;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class DbConfig {
    @Bean
    SessionFactory entityManager() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @PreDestroy
    void closeEntityManagerFactory() {
        entityManager().close();
    }
}
