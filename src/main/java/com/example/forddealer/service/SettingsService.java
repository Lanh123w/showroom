package com.example.forddealer.service;

import org.springframework.stereotype.Service;

import com.example.forddealer.model.Settings;
import com.example.forddealer.repository.SettingsRepository;

@Service
public class SettingsService {

    private final SettingsRepository repo;

    public SettingsService(SettingsRepository repo) {
        this.repo = repo;
    }

    // Lấy cấu hình hiện tại (ID = 1)
    public Settings getSettings() {
        return repo.findById(1).orElse(new Settings()); // tránh null
    }

    // Lưu hoặc cập nhật cấu hình
    public void save(Settings settings) {
        settings.setId(1); // đảm bảo luôn cập nhật dòng ID = 1
        repo.save(settings);
    }
}
