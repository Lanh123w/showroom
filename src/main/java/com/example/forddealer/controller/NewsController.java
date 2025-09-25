package com.example.forddealer.controller;

import com.example.forddealer.model.Settings;
import com.example.forddealer.service.NewsService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {

    private final NewsService newsService;
    private final SettingsService settingsService;
    private final CarService carService;

    public NewsController(NewsService newsService, SettingsService settingsService, CarService carService) {
        this.newsService = newsService;
        this.settingsService = settingsService;
        this.carService = carService;
    }

    @GetMapping("/news")
    public String showNewsPage(Model model) {
        model.addAttribute("newsList", newsService.getAll());

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
            settings.setFooterText("CITY FORD BÌNH TRIỆU - Đại lý chính thức hàng đầu của Ford Việt Nam");
        }

        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carService.getUsedCategories());

        return "news";
    }
}
