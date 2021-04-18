package ru.beerbis.springer.entity;

import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * "Продукт"
 */
public class Product {
    private final Integer id;
    private final String title;
    private final Integer cost;

    public Product(@NonNull Integer id,
                   @NonNull String title,
                   @NonNull Integer cost) {
        this.id = requireNonNull(id, "id");
        this.title = requireNonNull(title, "title");
        this.cost = requireNonNull(cost, "cost");

        if (cost < 0) {
            throw new IllegalArgumentException("cost may noy be negative, but: cost=" + cost);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCost() {
        return cost;
    }

    public static Builder builder(Product product) {
        return new Builder()
                .withId(product.id)
                .withTitle(product.title)
                .withCost(product.cost);
    }

    static class Builder {
        private Integer id;
        private String title;
        private Integer cost;

        private Builder() {
        }

        public Builder withId(Integer value){
            id = value;
            return this;
        }

        public Builder withTitle(String value){
            title = value;
            return this;
        }

        public Builder withCost(Integer value){
            cost = value;
            return this;
        }

        public Product build() {
            return new Product(id, title, cost);
        }
    }
}
