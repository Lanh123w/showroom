package com.example.forddealer.controller;

import com.example.forddealer.model.Contact;
import com.example.forddealer.service.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/contacts")
public class AdminContactController {

    @Autowired
    private ContactService contactService;

    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("admin") != null;
    }


    @GetMapping
    public String listContacts(Model model, HttpSession session, @ModelAttribute("message") String message) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/admin-login";
        }
        model.addAttribute("contacts", contactService.getAll());
        model.addAttribute("message", message);
        return "admin/contact-list";
    }

    @GetMapping("/view/{id}")
    public String viewContact(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/admin-login";
        }
        Contact contact = contactService.getById(id);
        if (contact == null) {
            model.addAttribute("error", "Không tìm thấy liên hệ.");
            return "redirect:/admin/contacts";
        }
        model.addAttribute("contact", contact);
        return "admin/contact-view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/admin-login";
        }
        contactService.delete(id);
        redirect.addFlashAttribute("message", "Xóa liên hệ thành công!");
        return "redirect:/admin/contacts";
    }
}
