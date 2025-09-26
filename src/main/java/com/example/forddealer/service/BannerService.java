package com.example.forddealer.service;

import com.example.forddealer.model.Banner;
import com.example.forddealer.repository.BannerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepo;

    public BannerService(BannerRepository bannerRepo) {
        this.bannerRepo = bannerRepo;
    }

    public List<Banner> getActiveBanners() {
        return bannerRepo.findByActiveTrueOrderBySortOrderAsc();
    }

    public List<Banner> getAll() {
        return bannerRepo.findAllByOrderBySortOrderAsc();
    }

    public Banner getById(Long id) {
        return bannerRepo.findById(id).orElse(null);
    }

    public void save(Banner banner) {
        bannerRepo.save(banner);
    }

    public void delete(Long id) {
        bannerRepo.deleteById(id);
    }

    public void updateSortOrder(List<Long> orderedIds) {
        for (int i = 0; i < orderedIds.size(); i++) {
            Banner b = getById(orderedIds.get(i));
            if (b != null) {
                b.setSortOrder(i);
                bannerRepo.save(b);
            }
        }
    }

    public void toggleActive(Long id, boolean active) {
        Banner b = getById(id);
        if (b != null) {
            b.setActive(active);
            bannerRepo.save(b);
        }
    }
}
