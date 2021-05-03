package ru.beerbis.springer.service;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.beerbis.springer.entity.Customer;
import ru.beerbis.springer.entity.Product;

import java.util.List;

@Service
public class PurchaseService {

    private final SessionFactory sessionFactory;

    public PurchaseService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Получить список уникальных товаров приобретённых покупателем
     *
     * @param customerId id покупателя
     * @return список номенклатурных позиций
     */
    public List<Product> getProductsOfCustomer(Integer customerId) {
        try(var session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            return session.createNamedQuery("Purchase.productsOfCustomer", Product.class)
                    .setParameter("customer", customerId)
                    .getResultList();
        }
    }

    /**
     * Получить список уникальных покупателей, купивших товар
     * 
     * @param productId идентификатор приобретённой номенклатурной позиции
     * @return присок приобетших покупателей
     */
    public List<Customer> getCustomersOfProduct(Integer productId) {
        try (var session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            return session.createNamedQuery("Purchase.customersOfProduct", Customer.class)
                    .setParameter("product", productId)
                    .getResultList();
        }
    }
}
