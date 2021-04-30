package ru.beerbis.springer.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.beerbis.springer.entity.Customer;
import ru.beerbis.springer.entity.Product;

@Repository
class CustomerDaoImpl extends BaseDaoImpl<Integer, Customer> implements CustomerDao {
    public CustomerDaoImpl(SessionFactory factory) {
        super(factory, Customer.class);
    }
}
