package ru.beerbis.springer.service.product;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.beerbis.springer.entity.Product;

import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
class ProductDaoImpl implements ProductDao {

    private final Logger log = LoggerFactory.getLogger(ProductDaoImpl.class);
    private final SessionFactory factory;

    public ProductDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Collection<Product> all() {
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            return session.createNamedQuery("Product.all", Product.class).getResultList();
        }
    }

    @Override
    public Optional<Product> find(@NonNull Integer id) {
        requireNonNull(id, "id");
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            return Optional.ofNullable(session.find(Product.class, id));
        }
    }

    @Override
    public boolean replace(Product product) {
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
            log.info("Product updated: {}", product);
            return true;
        }
    }

    @Override
    public void save(Product product) {
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
            log.info("Product stored: {}", product);
        }
    }

    public boolean remove(@NonNull Integer id) {
        requireNonNull(id, "id");
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            var updateCount = session.createNamedQuery("Product.del")
                    .setParameter("id", id)
                    .executeUpdate();

            if (updateCount == 0) {
                log.info("Product remove, not found: {}", id);
                return false;
            }

            session.getTransaction().commit();
            log.info("Product removed: {}", id);
            return true;
        }
    }
}
