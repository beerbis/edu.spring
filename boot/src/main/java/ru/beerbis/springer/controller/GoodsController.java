package ru.beerbis.springer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.beerbis.springer.model.Product;
import ru.beerbis.springer.repo.ProductDao;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static final Product newProductPlaceholder = new Product("новое новьё", 0);
    private final ProductDao productDao;

    public GoodsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("products", productDao.findAll());
        return "goods";
    }

    @GetMapping("/id{id}/edit")
    public String edit(Model model,
                       @PathVariable Integer id) {
        var product = productDao.findById(id);
        if (product.isEmpty()) {
            model.addAttribute("id", id);
            return "404";
        }

        model.addAttribute("product", product.orElseThrow());
        return "product-form";
    }

    @PostMapping("/save")
    @Transactional
    public String save(Model model, Product product) {
        if (product.getId() == newProductPlaceholder.getId()) {
            productDao.save(product);
        } else
        if (!productDao.existsLockingById(product.getId()))  {
            model.addAttribute("id", product.getId());
            return "404";
        } else {
            productDao.save(product);
        }
        return "redirect:/goods";
    }

    @GetMapping("/new")
    public String newOne(Model model) {
        model.addAttribute("product", newProductPlaceholder);
        return "product-form";
    }

    @GetMapping("/id{id}/del")
    public String remove(Model model,
                         @PathVariable Integer id) {
        if (productDao.deleteProductById(id) == 0) {
            model.addAttribute("id", id);
            return "404";
        };

        return "redirect:/goods";
    }
}
