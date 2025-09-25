package com.example.forddealer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.Settings;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.SettingsService;

@Controller
public class ProductController {

    private final CarService carService;
    private final SettingsService settingsService;

    public ProductController(CarService carService, SettingsService settingsService) {
        this.carService = carService;
        this.settingsService = settingsService;
    }

    @GetMapping("/product")
    public String showProducts(Model model) {
        List<Car> cars = carService.getAllCars();
        Settings settings = settingsService.getSettings();

        if (settings == null) {
            settings = new Settings();
            settings.setLogoPath("logo.jpg");
            settings.setBannerPath("banner.jpg");
            settings.setHotline("079-256-2288");
            settings.setEmail("info@cityford.vn");
            settings.setAddress("218 Quốc lộ 13, TP. Thủ Đức");
            settings.setZalo("0792562288");
            settings.setWebsite("https://www.fordbinhtrieu.com");
            settings.setAboutContent("City Ford là đại lý chính thức của Ford Việt Nam...");
            settings.setFooterText("CITY FORD BÌNH TRIỆU - Đại lý chính thức hàng đầu của Ford Việt Nam");
        }

        model.addAttribute("cars", cars);
        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carService.getUsedCategories());

        return "product";
    }
}
