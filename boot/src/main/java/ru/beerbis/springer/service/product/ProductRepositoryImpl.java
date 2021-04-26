package ru.beerbis.springer.service.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.beerbis.springer.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
class ProductRepositoryImpl implements ProductRepository {

    private final Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    private final EntityManager entityManager;
    private final TypedQuery<Product> queryGetAll;

    public ProductRepositoryImpl(
            @Qualifier("entityManager") EntityManager entityManager) {
        this.entityManager = entityManager;
        queryGetAll = entityManager.createNamedQuery("Product.all", Product.class);
    }

    @Override
    public Collection<Product> all() {
        return queryGetAll.getResultList();
    }

    @Override
    public Optional<Product> find(@NonNull Integer id) {
        requireNonNull(id, "id");
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    @Override
    public boolean replace(Product product) {
        var tr = entityManager.getTransaction();
        tr.begin();
        try {
            var merged = entityManager.merge(product);
            tr.commit();
            log.info("Product updated: {}", product);
            return true;
        } catch (Exception e) {
            tr.rollback();
            throw e;
        }
    }

    @Override
    public void persist(Product product) {
        var tr = entityManager.getTransaction();
        tr.begin();
        try {
            entityManager.persist(product);
            tr.commit();
            log.info("Product stored: {}", product);
        } catch (Exception e) {
            tr.rollback();
            throw e;
        }
    }

    public boolean remove(@NonNull Integer id) {
        requireNonNull(id, "id");
        var product = entityManager.find(Product.class, id);
        if (product == null) {
            log.info("Product remove, not found: {}", id);
            return false;
        }

        var tr = entityManager.getTransaction();
        tr.begin();
        try {
            entityManager.remove(product);
            tr.commit();
            log.info("Product removed: {}", product);
            return true;
        } catch (Exception e) {
            tr.rollback();
            throw e;
        }
    }
}
