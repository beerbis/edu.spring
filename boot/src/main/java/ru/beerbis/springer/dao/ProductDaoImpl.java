package ru.beerbis.springer.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.beerbis.springer.entity.Product;

@Repository
class ProductDaoImpl extends BaseDaoImpl<Integer, Product> implements ProductDao {
    public ProductDaoImpl(SessionFactory factory) {
        super(factory, Product.class);
    }
}
