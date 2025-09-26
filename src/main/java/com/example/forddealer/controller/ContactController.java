package com.example.forddealer.controller;

import com.example.forddealer.model.Contact;
import com.example.forddealer.model.Settings;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.BannerService;
import com.example.forddealer.repository.ContactRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {

    private final ContactRepository contactRepo;
    private final SettingsService settingsService;
    private final CarService carService;
    private final BannerService bannerService;

    public ContactController(ContactRepository contactRepo,
                             SettingsService settingsService,
                             CarService carService,
                             BannerService bannerService) {
        this.contactRepo = contactRepo;
        this.settingsService = settingsService;
        this.carService = carService;
        this.bannerService = bannerService;
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute Contact contact) {
        contactRepo.save(contact);
        return "redirect:/";
    }

    @GetMapping("/contact")
    public String contactForm(Model model) {
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            settings = new Settings();
            settings.setLogoPath("logo.jpg");
            settings.setHotline("079-256-2288");
            settings.setZalo("0792562288");
            settings.setWebsite("https://www.fordbinhtrieu.com");
        }

        model.addAttribute("settings", settings);
        model.addAttribute("contact", new Contact());
        model.addAttribute("carCategories", carService.getUsedCategories());
        model.addAttribute("banners", bannerService.getActiveBanners());

        return "contact";
    }
}
