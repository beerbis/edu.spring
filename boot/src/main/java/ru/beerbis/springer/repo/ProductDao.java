package ru.beerbis.springer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.beerbis.springer.model.Product;

import javax.transaction.Transactional;

public interface ProductDao extends JpaRepository<Product, Integer> {
    @Transactional
    Integer deleteProductById(Integer id);
}
