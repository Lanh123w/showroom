package com.example.forddealer.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forddealer.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {}
