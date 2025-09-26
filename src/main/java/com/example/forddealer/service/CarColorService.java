package com.example.forddealer.service;

import com.example.forddealer.model.CarColor;
import com.example.forddealer.repository.CarColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarColorService {

    private final CarColorRepository carColorRepository;

    @Autowired
    public CarColorService(CarColorRepository carColorRepository) {
        this.carColorRepository = carColorRepository;
    }

    // Lấy tất cả màu xe
    public List<CarColor> getAllColors() {
        return carColorRepository.findAll();
    }

    // Lưu hoặc cập nhật màu xe
    public CarColor saveColor(CarColor color) {
        return carColorRepository.save(color);
    }

    // Tìm màu theo ID
    public Optional<CarColor> getColorById(Long id) {
        return carColorRepository.findById(id);
    }

    // Xóa màu xe
    public void deleteColor(Long id) {
        carColorRepository.deleteById(id);
    }
}
