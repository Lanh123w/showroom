package com.example.forddealer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.CarCategory;
import com.example.forddealer.repository.CarRepository;

@Service
public class CarService {

    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public List<Car> getAllCars() {
        return repo.findAll();
    }

    public void save(Car car) {
        repo.save(car);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Car getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // ✅ Lấy danh sách loại xe duy nhất từ bảng car
    public List<CarCategory> getUsedCategories() {
        return repo.findDistinctCategoriesFromCar();
    }

    // ✅ Lấy danh sách xe theo categoryId
    public List<Car> getByCategoryId(Long categoryId) {
        return repo.findByCategoryId(categoryId);
    }
}
