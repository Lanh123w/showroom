package com.example.forddealer.controller;

import com.example.forddealer.model.CarCategory;
import com.example.forddealer.service.CarCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CarCategoryController {

    private final CarCategoryService service;

    public CarCategoryController(CarCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", service.getAll());
        return "admin/category-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new CarCategory());
        return "admin/category-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CarCategory category) {
        service.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", service.getById(id));
        return "admin/category-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/categories";
    }
}
