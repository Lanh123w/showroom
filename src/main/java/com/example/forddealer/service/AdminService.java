package com.example.forddealer.service;

import com.example.forddealer.model.Admin;
import com.example.forddealer.repository.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AdminService {

    private final AdminRepository repo;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    public Admin login(String username, String password) {
        Admin admin = repo.findByUsername(username);
        if (admin != null && new BCryptPasswordEncoder().matches(password, admin.getPassword())) {
            return admin;
        }
        return null;
    }
    public Admin getById(Long id) {
        return repo.findById(id).orElse(null);
    }
    
    public Admin getByUsername(String username) {
        return repo.findByUsername(username);
    }
    
    public List<Admin> getAll() {
        return repo.findAll();
    }
    
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
    public Admin save(Admin admin) {
        return repo.save(admin);
    }
    
}
