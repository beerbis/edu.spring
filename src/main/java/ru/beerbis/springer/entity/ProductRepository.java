package ru.beerbis.springer.entity;

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository {
    Collection<Product> all();
    Optional<Product> find(@NonNull Integer id);
    boolean replace(Product product);
    void newOne(Product product);
    boolean remove(Integer id);
}
