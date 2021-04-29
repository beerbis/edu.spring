package ru.beerbis.springer.entity;

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
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false, unique = true)
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

    public Integer getId() {
        return id;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
