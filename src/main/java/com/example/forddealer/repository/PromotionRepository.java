package com.example.forddealer.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.forddealer.model.Promotion;
import java.time.LocalDate;
import java.util.List;
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByEndDateAfter(LocalDate today);
}
