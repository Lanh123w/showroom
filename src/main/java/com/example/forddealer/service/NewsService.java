package com.example.forddealer.service;

import com.example.forddealer.model.News;
import com.example.forddealer.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepo;

    public NewsService(NewsRepository newsRepo) {
        this.newsRepo = newsRepo;
    }

    // Lấy tất cả tin tức
    public List<News> getAll() {
        return newsRepo.findAll();
    }

    // Lấy tin tức theo ID
    public News getById(Integer id) {
        return newsRepo.findById(id).orElse(null);
    }

    // Lưu hoặc cập nhật tin tức
    public void save(News news) {
        newsRepo.save(news);
    }

    // Xóa tin tức theo ID
    public void delete(Integer id) {
        newsRepo.deleteById(id);
    }
}
