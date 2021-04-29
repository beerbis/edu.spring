package ru.beerbis.springer.service.product;

import org.springframework.lang.NonNull;
import ru.beerbis.springer.entity.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductDao {
    Collection<Product> all();
    Optional<Product> find(@NonNull Integer id);
    boolean replace(Product product);
    void save(Product product);
    boolean remove(Integer id);
}
