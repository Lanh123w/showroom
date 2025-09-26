package com.example.forddealer.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.forddealer.model.Admin;
import com.example.forddealer.model.Car;
import com.example.forddealer.model.Settings;
import com.example.forddealer.service.AdminService;
import com.example.forddealer.service.CarCategoryService;
import com.example.forddealer.service.CarService;
import com.example.forddealer.service.SettingsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarService carService;
    private final SettingsService settingsService;
    private final AdminService adminService;
    private final CarCategoryService carCategoryService;


    public AdminController(CarService carService, SettingsService settingsService, AdminService adminService, CarCategoryService carCategoryService) {
        this.carService = carService;
        this.settingsService = settingsService;
        this.adminService = adminService;
        this.carCategoryService = carCategoryService;
    }

    
    @GetMapping("/login")
    public String loginForm() {
        return "admin/admin-login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "admin/admin-login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
    
    @GetMapping("/change-password")
    public String changePasswordForm(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) return "redirect:/admin/login";
        model.addAttribute("adminId", admin.getId());
        return "admin/change-password";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam Long adminId,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Model model,
                                 HttpSession session) {
        Admin admin = adminService.getById(adminId);
        if (admin == null || !new BCryptPasswordEncoder().matches(oldPassword, admin.getPassword())) {
            model.addAttribute("error", "Mật khẩu cũ không đúng");
            return "admin/change-password";
        }
    
        admin.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        adminService.save(admin);
        session.invalidate();
        return "redirect:/admin/login";
    }
    
    @GetMapping("/create-account")
    public String createAccountForm(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return "redirect:/admin/login";
        }
        model.addAttribute("newAdmin", new Admin());
        return "admin/create-account";
    }
    
    @PostMapping("/create-account")
    public String createAccount(@ModelAttribute Admin newAdmin,
                                HttpSession session,
                                Model model) {
        Admin current = (Admin) session.getAttribute("admin");
        if (current == null || !"ADMIN".equals(current.getRole())) {
            return "redirect:/admin/login";
        }
    
        if (adminService.getByUsername(newAdmin.getUsername()) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "admin/create-account";
        }
    
        newAdmin.setPassword(new BCryptPasswordEncoder().encode(newAdmin.getPassword()));
        newAdmin.setStatus("ACTIVE");
        newAdmin.setRole("ADMIN");
        adminService.save(newAdmin);
        return "redirect:/admin/accounts";
    }
    
    @GetMapping("/accounts")
    public String listAccounts(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return "redirect:/admin/login";
        }
        model.addAttribute("admins", adminService.getAll());
        return "admin/account-list";
    }
    
    @GetMapping("/edit-account/{id}")
    public String editAccountForm(@PathVariable Long id, HttpSession session, Model model) {
        Admin current = (Admin) session.getAttribute("admin");
        if (current == null || !"ADMIN".equals(current.getRole())) {
            return "redirect:/admin/login";
        }
        Admin target = adminService.getById(id);
        model.addAttribute("admin", target);
        return "admin/edit-account";
    }
    
    @PostMapping("/update-account")
    public String updateAccount(@ModelAttribute Admin admin,
                                HttpSession session) {
        Admin current = (Admin) session.getAttribute("admin");
        if (current == null || !"ADMIN".equals(current.getRole())) {
            return "redirect:/admin/login";
        }
    
        Admin existing = adminService.getById(admin.getId());
        existing.setEmail(admin.getEmail());
        existing.setRole(admin.getRole());
        existing.setStatus(admin.getStatus());
        adminService.save(existing);
        return "redirect:/admin/accounts";
    }
    
    @GetMapping("/delete-account/{id}")
    public String deleteAccount(@PathVariable Long id, HttpSession session) {
        Admin current = (Admin) session.getAttribute("admin");
        if (current == null || !"ADMIN".equals(current.getRole())) {
            return "redirect:/admin/login";
        }
    
        if (!current.getId().equals(id)) {
            adminService.delete(id);
        }
        return "redirect:/admin/accounts";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("settings", settingsService.getSettings());
        model.addAttribute("categories", carCategoryService.getAll());
        return "admin/dashboard";
    }
    
    

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car,
                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File destination = new File(uploadDir + fileName);
            imageFile.transferTo(destination);
            car.setImagePath(fileName);
        } else {
            car.setImagePath("default.jpg");
        }

        carService.save(car);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit-car/{id}")
public String editCarForm(@PathVariable Long id, Model model) {
    Car car = carService.getById(id);
    model.addAttribute("car", car);
    model.addAttribute("categories", carCategoryService.getAll());
    return "admin/edit-car";
}


    @PostMapping("/update-car")
    public String updateCar(@ModelAttribute Car car,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/images/";

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File destination = new File(uploadDir + fileName);
            imageFile.transferTo(destination);
            car.setImagePath(fileName);
        } else {
            Car oldCar = carService.getById(car.getId());
            car.setImagePath(oldCar.getImagePath());
        }

        carService.save(car);
        return "redirect:/admin/dashboard";
    }

  
    @GetMapping("/delete-car/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-settings")
    public String updateSettings(@ModelAttribute Settings settings,
                                 @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
                                 @RequestParam(value = "bannerFile", required = false) MultipartFile bannerFile) throws IOException {
        settings.setId(1);
        Settings old = settingsService.getSettings();
        String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();

        if (logoFile != null && !logoFile.isEmpty()) {
            if (old.getLogoPath() != null) {
                File oldLogo = new File(uploadDir + old.getLogoPath());
                if (oldLogo.exists()) oldLogo.delete();
            }
            String logoName = System.currentTimeMillis() + "_" + logoFile.getOriginalFilename().replaceAll("\\s+", "_");
            logoFile.transferTo(new File(uploadDir + logoName));
            settings.setLogoPath(logoName);
        } else {
            settings.setLogoPath(old.getLogoPath());
        }



        settingsService.save(settings);
        return "redirect:/admin/dashboard";
    }
}
