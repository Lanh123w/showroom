package com.example.forddealer.controller;

import com.example.forddealer.model.Settings;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.BannerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    private final SettingsService settingsService;
    private final CarService carService;
    private final BannerService bannerService;

    public AboutController(SettingsService settingsService,
                           CarService carService,
                           BannerService bannerService) {
        this.settingsService = settingsService;
        this.carService = carService;
        this.bannerService = bannerService;
    }

    @GetMapping("/about")
    public String about(Model model) {
        Settings settings = settingsService.getSettings();
        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carService.getUsedCategories());
        model.addAttribute("banners", bannerService.getActiveBanners());
        return "about";
    }
}
