package ru.beerbis.springer.dao;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.beerbis.springer.entity.Product;

import javax.annotation.PreDestroy;

@Configuration
public class DaoConfig {
    @Bean
    SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Bean
    ProductDao productDao() {
        return new ProductDaoImpl(sessionFactory(), Product.class);
    }

    @PreDestroy
    void closeEntityManagerFactory() {
        sessionFactory().close();
    }
}
