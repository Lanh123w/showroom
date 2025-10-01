package com.example.forddealer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.forddealer.model.TestDriveRequest;
import com.example.forddealer.service.TestDriveService;

@Controller
@RequestMapping("/admin/test-drive")
public class AdminTestDriveController {

    @Autowired
    private TestDriveService testDriveService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("requests", testDriveService.getAll());
        return "admin/admin-test-drive-list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        TestDriveRequest request = testDriveService.getById(id);
        model.addAttribute("request", request);
        return "admin/admin-test-drive-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TestDriveRequest request, RedirectAttributes redirectAttrs) {
        testDriveService.update(request);
        redirectAttrs.addFlashAttribute("message", "Cập nhật yêu cầu thành công!");
        return "redirect:/admin/test-drive/list";
    }
}
