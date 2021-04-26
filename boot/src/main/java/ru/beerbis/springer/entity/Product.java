package ru.beerbis.springer.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

import static java.util.Objects.requireNonNull;

/**
 * "Продукт"
 */
@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.all", query = "select p from Product p order by p.id"),
})
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private Integer cost;

    protected Product() {
    }

    public Product(@NonNull Integer id,
                   @NonNull String title,
                   @NonNull Integer cost) {
        this.id = id;
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
