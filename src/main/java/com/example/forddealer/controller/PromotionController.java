package com.example.forddealer.controller;

import com.example.forddealer.service.PromotionService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PromotionController {

    private final PromotionService promoService;
    private final SettingsService settingsService;
    private final CarService carService;

    public PromotionController(PromotionService promoService, SettingsService settingsService, CarService carService) {
        this.promoService = promoService;
        this.settingsService = settingsService;
        this.carService = carService;
    }

    @GetMapping("/promotions")
    public String showPromotions(Model model) {
        model.addAttribute("promotions", promoService.getActivePromotions());
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("carCategories", carService.getUsedCategories());

        return "promotions";
    }
}
