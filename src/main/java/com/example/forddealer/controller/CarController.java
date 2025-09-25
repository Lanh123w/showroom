package com.example.forddealer.controller;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.CarCategory;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.NewsService;
import com.example.forddealer.service.PromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;
    private final SettingsService settingsService;
    private final NewsService newsService;
    private final PromotionService promotionService;

    @Autowired
    public CarController(CarService carService,
                         SettingsService settingsService,
                         NewsService newsService,
                         PromotionService promotionService) {
        this.carService = carService;
        this.settingsService = settingsService;
        this.newsService = newsService;
        this.promotionService = promotionService;
    }


    @GetMapping("/show/{id}")
    public String showCar(@PathVariable Long id, Model model) {
        Car car = carService.getById(id);

        if (car == null) {
            model.addAttribute("errorMessage", "Xe không tồn tại hoặc đã bị xóa.");
            return "error";
        }

        if (car.getPrice() == 0) {
            car.setFormattedPrice("Chưa cập nhật");
        } else {
            String formattedPrice = String.format("%,d", car.getPrice());
            car.setFormattedPrice(formattedPrice.replace(",", "."));
        }

        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("car", car);
        model.addAttribute("carCategories", carService.getUsedCategories());

        return "car-detail";
    }

    @GetMapping("/category/{id}")
    public String showCarsByCategory(@PathVariable Long id, Model model) {
        List<Car> cars = carService.getByCategoryId(id);

        String categoryName = "Không xác định";
        if (!cars.isEmpty()) {
            CarCategory category = cars.get(0).getCategory();
            if (category != null && category.getName() != null) {
                categoryName = category.getName();
            }
        }
        model.addAttribute("cars", cars);
        model.addAttribute("selectedCategory", categoryName);
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("carCategories", carService.getUsedCategories());
        model.addAttribute("latestNews", newsService.getAll());
        model.addAttribute("latestPromotions", promotionService.getActivePromotions());

        return "car-list";
    }
}
