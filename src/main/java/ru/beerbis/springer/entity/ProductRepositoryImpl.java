package ru.beerbis.springer.entity;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
class ProductRepositoryImpl implements ProductRepository {

    private final Map<Integer, Product> productMap = List.of(
            new Product(1, "чай", 15),
            new Product(2, "кофе", 75),
            new Product(3, "кефир", 50),
            new Product(4, "молоко", 70),
            new Product(5, "смуззи", 150),
            new Product(6, "хрен на постном масле", 0)
    ).stream().collect(Collectors.toMap(Product::getId, Function.identity()));

    @Override
    public Collection<Product> all() {
        return productMap.values();
    }

    @Override
    public Optional<Product> find(@NonNull Integer id) {
        return Optional.ofNullable(productMap.get(requireNonNull(id, "id")));
    }
}
