package com.example.forddealer.controller;

import com.example.forddealer.model.Settings;
import com.example.forddealer.service.SettingsService;
import com.example.forddealer.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private CarService carService;

    @GetMapping("/about")
    public String about(Model model) {
        Settings settings = settingsService.getSettings();
        model.addAttribute("settings", settings);

        model.addAttribute("carCategories", carService.getUsedCategories());

        return "about";
    }
}
