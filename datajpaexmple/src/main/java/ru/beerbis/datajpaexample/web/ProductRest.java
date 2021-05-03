package ru.beerbis.datajpaexample.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.beerbis.datajpaexample.model.Product;
import ru.beerbis.datajpaexample.repo.ProductRepo;

import java.util.List;

@RestController
@RequestMapping(value = "/app/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRest {
    private final ProductRepo repo;

    public ProductRest(ProductRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    List<Product> getProducts() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    Product getProducts(
            @PathVariable Integer id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    Integer newOne(Product product) {
        repo.save(product);
        return product.getId();
    }

    @GetMapping("/delete/{id}")
    String delete(
            @PathVariable Integer id) {
        try {
            repo.deleteById(id);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}

