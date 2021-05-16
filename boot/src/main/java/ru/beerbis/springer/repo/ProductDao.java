package ru.beerbis.springer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import ru.beerbis.springer.model.Product;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

public interface ProductDao extends JpaRepository<Product, Integer> {
    @Transactional
    Integer deleteProductById(Integer id);

    @Transactional
    @Lock(LockModeType.WRITE)
    boolean existsLockingById(Integer id);
}
