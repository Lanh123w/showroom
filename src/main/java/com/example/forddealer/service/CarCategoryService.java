package com.example.forddealer.service;

import com.example.forddealer.model.CarCategory;
import com.example.forddealer.repository.CarCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarCategoryService {

    private final CarCategoryRepository repo;

    public CarCategoryService(CarCategoryRepository repo) {
        this.repo = repo;
    }

    public List<CarCategory> getAll() {
        return repo.findAll();
    }

    public CarCategory getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void save(CarCategory category) {
        repo.save(category);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}