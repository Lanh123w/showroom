package com.example.forddealer.repository;
import com.example.forddealer.model.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CarCategoryRepository extends JpaRepository<CarCategory, Long> {}
