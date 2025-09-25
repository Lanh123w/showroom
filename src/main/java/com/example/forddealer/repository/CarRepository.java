package com.example.forddealer.repository;

import com.example.forddealer.model.Car;
import com.example.forddealer.model.CarCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findAll();

    List<Car> findByCategoryId(Long categoryId);

    @Query("SELECT DISTINCT c.category FROM Car c")
    List<CarCategory> findDistinctCategoriesFromCar();
}
