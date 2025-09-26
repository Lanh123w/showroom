package com.example.forddealer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.forddealer.model.Settings;
import com.example.forddealer.model.Car;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarCategoryService;
import com.example.forddealer.service.BannerService;

import java.util.List;

@Controller
public class HomeController {

    private final CarService carService;
    private final SettingsService settingsService;
    private final CarCategoryService carCategoryService;
    private final BannerService bannerService;

  
    public HomeController(CarService carService,
                          SettingsService settingsService,
                          CarCategoryService carCategoryService,
                          BannerService bannerService) {
        this.carService = carService;
        this.settingsService = settingsService;
        this.carCategoryService = carCategoryService;
        this.bannerService = bannerService;
    }

    @GetMapping("/")
    public String index(Model model) {
      
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = new Settings();
            settings.setHotline("Chưa cấu hình");
            settings.setEmail("Chưa cấu hình");
            settings.setAddress("Chưa cấu hình");
            settings.setFooterText("City Ford");
            settings.setAboutContent("Chưa có nội dung giới thiệu.");
            settings.setLogoPath("logo.jpg");
            settings.setZalo("Chưa cấu hình");
            settings.setWebsite("Chưa cấu hình");
        }

      
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            String formattedPrice = String.format("%,d", car.getPrice());
            car.setFormattedPrice(formattedPrice); 
        }

        model.addAttribute("cars", cars);
        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carCategoryService.getAll());
        model.addAttribute("banners", bannerService.getActiveBanners()); 

        return "index";
    }
}
