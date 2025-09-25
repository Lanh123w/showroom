package com.example.forddealer.controller;

import com.example.forddealer.model.ServiceContent;
import com.example.forddealer.service.ServiceContentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/services")
public class ServiceContentController {

    private final ServiceContentService service;

    public ServiceContentController(ServiceContentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        model.addAttribute("services", service.getAll());
        return "admin/service-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("serviceContent", new ServiceContent());
        return "admin/service-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ServiceContent serviceContent) {
        service.save(serviceContent);
        return "redirect:/admin/services";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("serviceContent", service.getById(id));
        return "admin/service-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/services";
    }
}
