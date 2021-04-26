package ru.beerbis.springer.service.product;

import org.springframework.lang.NonNull;
import ru.beerbis.springer.entity.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository {
    Collection<Product> all();
    Optional<Product> find(@NonNull Integer id);
    boolean replace(Product product);
    void persist(Product product);
    boolean remove(Integer id);
}
