package ru.beerbis.springer.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer",
        uniqueConstraints = {@UniqueConstraint(name = "UQ_CUSTOMER_NAME", columnNames = "name")})
@NamedQueries({
        @NamedQuery(name = "Customer.all", query = "select c from Customer c order by c.id"),
        @NamedQuery(name = "Customer.del", query = "delete from Customer c where c.id = :id")
})
public class Customer extends BaseEntity<Integer> {

    @Column(name = "name",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<Purchase> purchases;

    protected Customer() {
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
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
