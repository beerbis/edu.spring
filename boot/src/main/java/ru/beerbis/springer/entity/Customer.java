package ru.beerbis.springer.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer",
        uniqueConstraints = {@UniqueConstraint(name = "UQ_CUSTOMER_NAME", columnNames = "name")})
public class Customer {

    @Id
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<Purchase> purchases;

    protected Customer() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
