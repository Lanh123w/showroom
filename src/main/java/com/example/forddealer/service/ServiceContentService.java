package com.example.forddealer.service;

import com.example.forddealer.model.ServiceContent;
import com.example.forddealer.repository.ServiceContentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceContentService {

    private final ServiceContentRepository repo;

    public ServiceContentService(ServiceContentRepository repo) {
        this.repo = repo;
    }

    public List<ServiceContent> getAll() {
        return repo.findAll();
    }

    public ServiceContent getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void save(ServiceContent content) {
        repo.save(content);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
