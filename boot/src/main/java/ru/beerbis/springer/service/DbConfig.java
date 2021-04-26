package ru.beerbis.springer.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
public class DbConfig {
    @Bean
    EntityManager entityManager() {
        EntityManagerFactory factory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        return factory.createEntityManager();
    }
}
