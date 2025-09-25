package com.example.forddealer.controller;

import com.example.forddealer.model.Settings;
import com.example.forddealer.model.ServiceContent;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.ServiceContentService;
import com.example.forddealer.service.CarService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ServicesController {

    private final SettingsService settingsService;
    private final ServiceContentService serviceContentService;
    private final CarService carService;

    public ServicesController(SettingsService settingsService, ServiceContentService serviceContentService, CarService carService) {
        this.settingsService = settingsService;
        this.serviceContentService = serviceContentService;
        this.carService = carService;
    }

    @GetMapping("/services")
    public String services(Model model) {
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = new Settings();
            settings.setLogoPath("logo.jpg");
            settings.setHotline("079-256-2288");
            settings.setZalo("0792562288");
            settings.setWebsite("https://www.fordbinhtrieu.com");
        }

        List<ServiceContent> services = serviceContentService.getAll();

        model.addAttribute("settings", settings);
        model.addAttribute("services", services);
        model.addAttribute("carCategories", carService.getUsedCategories());

        return "services";
    }
}
