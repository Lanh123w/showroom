package com.example.forddealer.controller;

import com.example.forddealer.model.Promotion;
import com.example.forddealer.service.CloudinaryService;
import com.example.forddealer.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin/promotion-form")
public class AdminPromotionController {

    private final PromotionService promoService;
    private final CloudinaryService cloudinaryService;

    public AdminPromotionController(PromotionService promoService, CloudinaryService cloudinaryService) {
        this.promoService = promoService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public String dashboard(Model model,
                            @ModelAttribute("message") String message) {
        model.addAttribute("promotion", new Promotion());
        model.addAttribute("promotions", promoService.getAll());
        model.addAttribute("message", message);
        return "admin/promotion-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Promotion promo = promoService.getById(id);
        model.addAttribute("promotion", promo != null ? promo : new Promotion());
        model.addAttribute("promotions", promoService.getAll());
        return "admin/promotion-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Promotion promotion,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(imageFile);
            promotion.setImagePath(imageUrl);
        } else if (promotion.getId() != null) {
            Promotion old = promoService.getById(promotion.getId());
            if (old != null) {
                promotion.setImagePath(old.getImagePath());
            }
        }

        promoService.save(promotion);
        String action = (promotion.getId() != null) ? "Cập nhật" : "Thêm mới";
        redirectAttributes.addFlashAttribute("message", action + " khuyến mãi thành công!");
        return "redirect:/admin/promotion-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        promoService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa khuyến mãi thành công!");
        return "redirect:/admin/promotion-form";
    }
}
