package ru.beerbis.springer.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<IdT extends Serializable> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",
            nullable = false)
    private IdT id;

    public IdT getId() {
        return id;
    }

    public void setId(IdT id) {
        this.id = id;
    }
}
