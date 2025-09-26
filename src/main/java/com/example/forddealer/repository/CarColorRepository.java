package com.example.forddealer.repository;

import com.example.forddealer.model.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CarColorRepository extends JpaRepository<CarColor, Long> {

    List<CarColor> findByNameContainingIgnoreCase(String keyword);
}
