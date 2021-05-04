package ru.beerbis.springer.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

import static java.util.Objects.requireNonNull;

/**
 * "Продукт"
 */
@Entity
@Table(name = "product",
        uniqueConstraints = {@UniqueConstraint(name = "UQ_PRODUCT_TITLE", columnNames = "title")})
@NamedQueries({
        @NamedQuery(name = "Product.all", query = "select p from Product p order by p.id"),
        @NamedQuery(name = "Product.del", query = "delete from Product p where p.id = :id")
})
public class Product extends BaseEntity<Integer> {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    protected Product() {
    }

    public Product(@NonNull String title,
                   @NonNull Integer cost) {
        this.title = title;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
