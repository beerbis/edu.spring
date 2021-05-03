package ru.beerbis.datajpaexample.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

/**
 * "Продукт"
 */
@Entity
@Table(name = "product")
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
