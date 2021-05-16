package ru.beerbis.springer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.beerbis.springer.model.Product;
import ru.beerbis.springer.repo.ProductDao;
import ru.beerbis.springer.repo.error.EntityNotFound;
import ru.beerbis.springer.repo.error.ForbiddenInputValue;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api-v1/products")
public class ProductsRestController {

    private final ProductDao productDao;

    public ProductsRestController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping()
    public List<Product> all() {
        return productDao.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Integer id) {
        return productDao.findById(id).orElseThrow(() -> new EntityNotFound(Product.class, id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new ForbiddenInputValue(Product.class, "id", product.getId(),
                    "creation may not update existing entity, or insert user-desired ID");
        }
        return productDao.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        if (productDao.deleteProductById(id) == 0) {
            throw new EntityNotFound(Product.class, id);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public Product update(@PathVariable Integer id, @RequestBody Product product) {
        if (product.getId() != null && !id.equals(product.getId())) {
            throw new ForbiddenInputValue(Product.class, "id", product.getId(),
                    "updating entity may not change it's own ID");
        }
        if (!productDao.existsLockingById(id)) {
            throw new EntityNotFound(Product.class, id);
        }

        product.setId(id);
        return productDao.save(product);
    }
}
