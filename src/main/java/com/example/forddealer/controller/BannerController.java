package com.example.forddealer.controller;

import com.example.forddealer.model.Banner;
import com.example.forddealer.service.BannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public String manageBanners(Model model) {
        model.addAttribute("banners", bannerService.getAll());
        model.addAttribute("banner", new Banner());
        return "admin/banner-manage";
    }

    @GetMapping("/edit/{id}")
    public String editBanner(@PathVariable Long id, Model model) {
        model.addAttribute("banners", bannerService.getAll());
        model.addAttribute("banner", bannerService.getById(id));
        return "admin/banner-manage";
    }

    @PostMapping("/save")
    public String saveBanner(@ModelAttribute Banner banner,
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
            File dest = new File(uploadDir + fileName);
            dest.getParentFile().mkdirs();
            imageFile.transferTo(dest);
            banner.setImagePath(fileName);
        }

        bannerService.save(banner);
        return "redirect:/admin/banners";
    }

    @PostMapping("/sort")
    @ResponseBody
    public String updateSortOrder(@RequestBody List<Long> orderedIds) {
        bannerService.updateSortOrder(orderedIds);
        return "OK";
    }

    @PostMapping("/toggle")
    @ResponseBody
    public String toggleBanner(@RequestParam Long id, @RequestParam boolean active) {
        bannerService.toggleActive(id, active);
        return "OK";
    }

    @GetMapping("/delete/{id}")
    public String deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return "redirect:/admin/banners";
    }
}
