package com.example.forddealer.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.forddealer.model.Installment;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.InstallmentService;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.BannerService;

@Controller
@RequestMapping("/installment")
public class InstallmentController {

    private final InstallmentService installmentService;
    private final SettingsService settingsService;
    private final CarService carService;
    private final BannerService bannerService;

    @Autowired
    public InstallmentController(InstallmentService installmentService,
                                 SettingsService settingsService,
                                 CarService carService,
                                 BannerService bannerService) {
        this.installmentService = installmentService;
        this.settingsService = settingsService;
        this.carService = carService;
        this.bannerService = bannerService;
    }

    @GetMapping
    public String form(Model model) {
        model.addAttribute("installment", new Installment());
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("banners", bannerService.getActiveBanners());
        return "installment-form";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Installment installment, Model model) {
        installment.setCreatedAt(LocalDateTime.now());

        double interestRate = installment.getInterestRate() != null ? installment.getInterestRate() : 0.0;
        double carPrice = installment.getCarPrice() != null ? installment.getCarPrice() : 0.0;
        double down = installment.getDownPayment() != null ? installment.getDownPayment() : 0.0;
        int months = installment.getTermMonths() != null ? installment.getTermMonths() : 1;

        double remaining = carPrice - down;
        double monthly;

        if (interestRate > 0) {
            double monthlyRate = interestRate / 100 / 12;
            monthly = remaining * monthlyRate / (1 - Math.pow(1 + monthlyRate, -months));
        } else {
            monthly = remaining / months;
        }

        installment.setMonthlyPayment(Math.round(monthly * 100.0) / 100.0);
        installmentService.save(installment);

        model.addAttribute("installment", new Installment());
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("banners", bannerService.getActiveBanners());
        model.addAttribute("success", "✅ Đăng ký trả góp thành công! Chúng tôi sẽ liên hệ sớm.");
        return "installment-form";
    }
}
