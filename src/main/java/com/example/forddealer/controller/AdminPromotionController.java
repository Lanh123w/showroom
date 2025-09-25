package com.example.forddealer.controller;

import com.example.forddealer.model.Promotion;
import com.example.forddealer.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/promotion-form")
public class AdminPromotionController {

    private final PromotionService promoService;

    public AdminPromotionController(PromotionService promoService) {
        this.promoService = promoService;
    }

    // Hiển thị form và danh sách khuyến mãi
    @GetMapping
    public String dashboard(Model model,
                            @ModelAttribute("message") String message) {
        model.addAttribute("promotion", new Promotion());
        model.addAttribute("promotions", promoService.getAll());
        model.addAttribute("message", message); // hiển thị thông báo nếu có
        return "admin/promotion-form";
    }

    // Hiển thị form sửa khuyến mãi
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Promotion promo = promoService.getById(id);
        model.addAttribute("promotion", promo != null ? promo : new Promotion());
        model.addAttribute("promotions", promoService.getAll());
        return "admin/promotion-form";
    }

    // Lưu khuyến mãi (thêm mới hoặc cập nhật)
    @PostMapping("/save")
    public String save(@ModelAttribute Promotion promotion,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(uploadDir + fileName));
            promotion.setImagePath(fileName);
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

    // Xóa khuyến mãi
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        promoService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa khuyến mãi thành công!");
        return "redirect:/admin/promotion-form";
    }
}
