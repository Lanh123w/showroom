package com.example.forddealer.config;

import com.example.forddealer.model.Admin;
import com.example.forddealer.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(AdminRepository adminRepository) {
        return args -> {
            if (adminRepository.findByUsername("admin") == null) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
                admin.setEmail("admin@example.com");
                admin.setRole("ADMIN");
                admin.setStatus("ACTIVE");
                adminRepository.save(admin);
                System.out.println("✅ Tài khoản admin mặc định đã được tạo.");
            } else {
                System.out.println("⚠️ Tài khoản admin mặc định đã tồn tại.");
            };
        };
    }
}
