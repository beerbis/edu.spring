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

    private final Map<Integer, Product> productMap;
    private final ProductIdSequence sequence;

    public ProductRepositoryImpl(ProductIdSequence sequence) {
        productMap = List.of(
                new Product(sequence.getNext(), "чай", 15),
                new Product(sequence.getNext(), "кофе", 75),
                new Product(sequence.getNext(), "кефир", 50),
                new Product(sequence.getNext(), "молоко", 70),
                new Product(sequence.getNext(), "смуззи", 150),
                new Product(sequence.getNext(), "хрен на постном масле", 0)
        ).stream().collect(Collectors.toConcurrentMap(Product::getId, Function.identity()));
        this.sequence = sequence;
    }

    @Override
    public Collection<Product> all() {
        return productMap.values();
    }

    @Override
    public Optional<Product> find(@NonNull Integer id) {
        return Optional.ofNullable(productMap.get(requireNonNull(id, "id")));
    }

    @Override
    public boolean replace(Product product) {
        return productMap.replace(product.getId(), product) != null;
    }

    @Override
    public void newOne(Product product) {
        productMap.compute(sequence.getNext(), (id, values) -> Product.builder(product).withId(id).build());
    }

    public boolean remove(Integer id) {
        return productMap.remove(id) != null;
    }


}
