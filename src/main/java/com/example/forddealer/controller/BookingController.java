package com.example.forddealer.controller;

import com.example.forddealer.model.Booking;
import com.example.forddealer.model.Settings;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;
import com.example.forddealer.repository.BookingRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    private final BookingRepository bookingRepo;
    private final SettingsService settingsService;
    private final CarService carService;

    public BookingController(BookingRepository bookingRepo, SettingsService settingsService, CarService carService) {
        this.bookingRepo = bookingRepo;
        this.settingsService = settingsService;
        this.carService = carService;
    }

    @PostMapping("/booking")
    public String submitBooking(@ModelAttribute Booking booking) {
        if (booking.getCreatedAt() == null) {
            booking.setCreatedAt(java.time.LocalDateTime.now());
        }

        bookingRepo.save(booking);
        return "redirect:/";
    }

    @GetMapping("/booking")
    public String bookingForm(Model model) {
        model.addAttribute("booking", new Booking());

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

        model.addAttribute("settings", settings);

        model.addAttribute("carCategories", carService.getUsedCategories());

        return "booking";
    }
}
