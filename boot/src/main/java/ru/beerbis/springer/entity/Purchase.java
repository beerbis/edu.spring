package ru.beerbis.springer.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Purchase.customersOfProduct", query = "select distinct c from Purchase pu join fetch Customer c where pu.product.id = :product"),
        @NamedQuery(name = "Purchase.productsOfCustomer", query = "select distinct p from Purchase pu join fetch Product p where pu.customer.id = :customer")
})
@Table(name = "purchase", indexes = {
        @Index(name = "customer_idx", columnList = "customer_id"),
        @Index(name = "product_idx", columnList = "product_id")
})
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_CUSTOMER", value = ConstraintMode.CONSTRAINT))
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PRODUCT", value = ConstraintMode.CONSTRAINT))
    private Product product;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "count", nullable = false)
    private Integer count;

    protected Purchase() {
    }

    public Purchase(Customer customer, Product product, Integer count) {
        this(customer, product, product.getCost(), count);
    }
    
    public Purchase(Customer customer, Product product, Integer price, Integer count) {
        this.customer = customer;
        this.product = product;
        this.price = price;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }
}
