package com.example.forddealer.controller;

import com.example.forddealer.model.Settings;
import com.example.forddealer.model.News;
import com.example.forddealer.service.NewsService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.BannerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsController {

    private final NewsService newsService;
    private final SettingsService settingsService;
    private final CarService carService;
    private final BannerService bannerService;

    public NewsController(NewsService newsService,
                          SettingsService settingsService,
                          CarService carService,
                          BannerService bannerService) {
        this.newsService = newsService;
        this.settingsService = settingsService;
        this.carService = carService;
        this.bannerService = bannerService;
    }

    @GetMapping("/news")
    public String showNewsPage(Model model) {
        model.addAttribute("newsList", newsService.getAll());

        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = getDefaultSettings();
        }

        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carService.getUsedCategories());
        model.addAttribute("banners", bannerService.getActiveBanners());

        return "news";
    }

    @GetMapping("/news/{id}")
    public String viewNewsDetail(@PathVariable Integer id, Model model) {
        News news = newsService.getById(id);
        if (news == null) {
            return "redirect:/news";
        }

        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = getDefaultSettings();
        }

        model.addAttribute("news", news);
        model.addAttribute("settings", settings);
        model.addAttribute("carCategories", carService.getUsedCategories());
        model.addAttribute("banners", bannerService.getActiveBanners());

        return "detail";
    }

    private Settings getDefaultSettings() {
        Settings settings = new Settings();
        settings.setLogoPath("logo.jpg");
        settings.setHotline("079-256-2288");
        settings.setEmail("info@cityford.vn");
        settings.setAddress("218 Quốc lộ 13, TP. Thủ Đức");
        settings.setZalo("0792562288");
        settings.setWebsite("https://www.fordbinhtrieu.com");
        settings.setFooterText("CITY FORD BÌNH TRIỆU - Đại lý chính thức hàng đầu của Ford Việt Nam");
        return settings;
    }
}
