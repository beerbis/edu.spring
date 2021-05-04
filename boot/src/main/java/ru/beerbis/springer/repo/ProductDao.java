package ru.beerbis.springer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.beerbis.springer.model.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
