package ru.beerbis.springer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.beerbis.springer.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
}
