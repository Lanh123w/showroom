package com.example.forddealer.service;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.CarCategory;
import com.example.forddealer.model.CarColor;
import com.example.forddealer.repository.CarRepository;
import com.example.forddealer.repository.CarColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarColorRepository carColorRepository;

    @Autowired
    public CarService(CarRepository carRepository, CarColorRepository carColorRepository) {
        this.carRepository = carRepository;
        this.carColorRepository = carColorRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void save(Car car) {
        carRepository.save(car);
    }


    public void delete(Long id) {
        carRepository.deleteById(id);
    }


    public Car getById(Long id) {
        return carRepository.findById(id).orElse(null);
    }


    public List<Car> getAllCarsWithColors() {
        return carRepository.findAll();
    }


    public List<CarCategory> getUsedCategories() {
        return carRepository.findDistinctCategoriesFromCar();
    }


    public List<Car> getByCategoryId(Long categoryId) {
        return carRepository.findByCategoryId(categoryId);
    }


    public void addColorToCar(Long carId, Long colorId) {
        Car car = carRepository.findById(carId).orElse(null);
        CarColor color = carColorRepository.findById(colorId).orElse(null);
        if (car != null && color != null) {
            car.getColors().add(color);
            carRepository.save(car);
        }
    }


    public void removeColorFromCar(Long carId, Long colorId) {
        Car car = carRepository.findById(carId).orElse(null);
        CarColor color = carColorRepository.findById(colorId).orElse(null);
        if (car != null && color != null) {
            car.getColors().remove(color);
            carRepository.save(car);
        }
    }
}
