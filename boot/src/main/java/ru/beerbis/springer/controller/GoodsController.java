package ru.beerbis.springer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.beerbis.springer.entity.Product;
import ru.beerbis.springer.service.product.ProductDao;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Product newProductPlaceholder = new Product(null, "новое новьё", 0);
    private final ProductDao productDao;

    public GoodsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("products", productDao.all());
        return "goods";
    }

    @GetMapping("/id{id}/edit")
    public String edit(Model model,
                       @PathVariable Integer id) {
        var product = productDao.find(id);
        if (product.isEmpty()) {
            model.addAttribute("id", id);
            return "404";
        }

        model.addAttribute("product", product.orElseThrow());
        return "product-form";
    }

    @PostMapping("/save")
    public String edit(Model model, Product product) {
        if (product.getId() == newProductPlaceholder.getId()) {
            productDao.save(product);
        } else
        if (!productDao.replace(product))  {
            model.addAttribute("id", product.getId());
            return "404";
        };
        
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
        if (!productDao.remove(id)) {
            model.addAttribute("id", id);
            return "404";
        };

        return "redirect:/goods";
    }
}
