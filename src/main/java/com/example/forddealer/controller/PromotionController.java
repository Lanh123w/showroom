package com.example.forddealer.controller;

import com.example.forddealer.model.Promotion;
import com.example.forddealer.model.Settings;
import com.example.forddealer.service.PromotionService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.BannerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PromotionController {

    private final PromotionService promotionService;
    private final SettingsService settingsService;
    private final CarService carService;
    private final BannerService bannerService;

    public PromotionController(PromotionService promotionService,
                               SettingsService settingsService,
                               CarService carService,
                               BannerService bannerService) {
        this.promotionService = promotionService;
        this.settingsService = settingsService;
        this.carService = carService;
        this.bannerService = bannerService;
    }

    @GetMapping("/promotions")
    public String showPromotions(Model model) {
        model.addAttribute("promotions", promotionService.getActivePromotions());
        addCommonAttributes(model);
        model.addAttribute("banners", bannerService.getActiveBanners());
        return "promotions";
    }

    @GetMapping("/promotions/{id}")
    public String viewPromotionDetail(@PathVariable Long id, Model model) {
        Promotion promotion = promotionService.getById(id);
        if (promotion == null) {
            return "redirect:/promotions";
        }
        model.addAttribute("promotion", promotion);
        addCommonAttributes(model);
        model.addAttribute("banners", bannerService.getActiveBanners());
        return "promotion-detail";
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("settings", getSettingsOrDefault());
        model.addAttribute("carCategories", carService.getUsedCategories());
    }

    private Settings getSettingsOrDefault() {
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = new Settings();
            settings.setLogoPath("logo.jpg");
            settings.setHotline("079-256-2288");
            settings.setEmail("info@cityford.vn");
            settings.setAddress("218 Quốc lộ 13, TP. Thủ Đức");
            settings.setZalo("0792562288");
            settings.setWebsite("https://www.fordbinhtrieu.com");
            settings.setFooterText("CITY FORD BÌNH TRIỆU - Đại lý chính thức hàng đầu của Ford Việt Nam");
        }
        return settings;
    }
}
