package com.example.forddealer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.forddealer.model.Settings;
import com.example.forddealer.model.Car;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarCategoryService;
import java.util.List;

@Controller
public class HomeController {

    private final CarService carService;
    private final SettingsService settingsService;
    private final CarCategoryService carCategoryService;

    public HomeController(CarService carService, SettingsService settingsService, CarCategoryService carCategoryService) {
        this.carService = carService;
        this.settingsService = settingsService;
        this.carCategoryService = carCategoryService;

    }

    @GetMapping("/")
    public String index(Model model) {
        // Lấy cấu hình website
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = new Settings();
            settings.setHotline("Chưa cấu hình");
            settings.setEmail("Chưa cấu hình");
            settings.setAddress("Chưa cấu hình");
            settings.setFooterText("City Ford");
            settings.setAboutContent("Chưa có nội dung giới thiệu.");
            settings.setLogoPath("logo.jpg");
            settings.setBannerPath("banner.jpg");
            settings.setZalo("Chưa cấu hình");
            settings.setWebsite("Chưa cấu hình");
        }

        // Lấy danh sách xe và format giá
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            String formattedPrice = String.format("%,d", car.getPrice()); // ví dụ: 1,199,000,000
            car.setFormattedPrice(formattedPrice); // cần thêm field formattedPrice trong Car.java
        }

        model.addAttribute("cars", cars);
        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carCategoryService.getAll());


        return "index";
    }
}
