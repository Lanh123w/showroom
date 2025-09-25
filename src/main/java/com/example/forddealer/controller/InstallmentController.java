package com.example.forddealer.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forddealer.model.Installment;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.InstallmentService;
import com.example.forddealer.service.SettingsService;

@Controller
@RequestMapping("/installment")
public class InstallmentController {

    private final InstallmentService installmentService;
    private final SettingsService settingsService;
    private final CarService carService;

    @Autowired
    public InstallmentController(InstallmentService installmentService,
                                 SettingsService settingsService,
                                 CarService carService) {
        this.installmentService = installmentService;
        this.settingsService = settingsService;
        this.carService = carService;
    }

    @GetMapping
    public String form(Model model) {
        model.addAttribute("installment", new Installment());
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("cars", carService.getAllCars());
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
        double monthly = 0.0;

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
        model.addAttribute("success", "✅ Đăng ký trả góp thành công! Chúng tôi sẽ liên hệ sớm.");
        return "installment-form";
    }
}
