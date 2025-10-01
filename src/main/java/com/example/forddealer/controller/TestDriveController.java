package com.example.forddealer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.forddealer.model.TestDriveRequest;
import com.example.forddealer.service.BannerService;
import com.example.forddealer.service.CarCategoryService;
import com.example.forddealer.service.NewsService;
import com.example.forddealer.service.PromotionService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.TestDriveService;

@Controller
@RequestMapping("/test-drive")
public class TestDriveController {

    @Autowired
    private TestDriveService testDriveService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private CarCategoryService carCategoryService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/form")
    public String showForm(@RequestParam String carName, Model model) {
        TestDriveRequest request = new TestDriveRequest();
        request.setCarName(carName);

        model.addAttribute("request", request);
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("carCategories", carCategoryService.getAll());
        model.addAttribute("banners", bannerService.getAll());
        model.addAttribute("latestNews", newsService.getAll());
        model.addAttribute("latestPromotions", promotionService.getActivePromotions());

        return "test-drive-form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute TestDriveRequest request, RedirectAttributes redirectAttrs) {
        testDriveService.save(request);
        redirectAttrs.addFlashAttribute("message", "Đặt lịch lái thử thành công!");
        return "redirect:/";
    }
}
