package ru.beerbis.springer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Магазин на д.Иване");
        return "index";
    }
}
