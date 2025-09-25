package com.example.forddealer.service;
import com.example.forddealer.model.Promotion;
import com.example.forddealer.repository.PromotionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository repo;

    public PromotionService(PromotionRepository repo) {
        this.repo = repo;
    }

    public List<Promotion> getAll() {
        return repo.findAll();
    }

    public List<Promotion> getActivePromotions() {
        return repo.findByEndDateAfter(LocalDate.now());
    }

    public Promotion getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void save(Promotion promo) {
        repo.save(promo);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
