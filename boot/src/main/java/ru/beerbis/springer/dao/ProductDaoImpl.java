package ru.beerbis.springer.dao;

import org.hibernate.SessionFactory;
import ru.beerbis.springer.entity.Product;

class ProductDaoImpl extends BaseDaoImpl<Integer, Product> implements ProductDao {
    public ProductDaoImpl(SessionFactory factory, Class<Product> clazz) {
        super(factory, clazz);
    }
}
