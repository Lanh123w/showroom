package com.example.forddealer.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.forddealer.model.News;
import com.example.forddealer.service.NewsService;

@Controller
@RequestMapping("/admin/news-form")
public class AdminNewsController {

    private final NewsService newsService;

    public AdminNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String dashboard(Model model,
                            @ModelAttribute("message") String message) {
        model.addAttribute("news", new News());
        model.addAttribute("newsList", newsService.getAll());
        model.addAttribute("message", message);
        return "admin/news-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model,
                           @ModelAttribute("message") String message) {
        News item = newsService.getById(id);
        model.addAttribute("news", item != null ? item : new News());
        model.addAttribute("newsList", newsService.getAll());
        model.addAttribute("message", message);
        return "admin/news-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute News news,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (news.getCreatedAt() == null) {
            news.setCreatedAt(LocalDateTime.now());
        }

        String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(uploadDir + fileName));
            news.setImagePath(fileName);
        } else if (news.getId() != null) {
            News old = newsService.getById(news.getId());
            if (old != null) {
                news.setImagePath(old.getImagePath());
            }
        }

        newsService.save(news);
        String action = (news.getId() != null) ? "Cập nhật" : "Thêm mới";
        redirectAttributes.addFlashAttribute("message", action + " tin tức thành công!");
        return "redirect:/admin/news-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        newsService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa tin tức thành công!");
        return "redirect:/admin/news-form";
    }
}
