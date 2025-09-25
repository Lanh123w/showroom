package com.example.forddealer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.forddealer.model.Installment;
import com.example.forddealer.repository.InstallmentRepository;

@Service
public class InstallmentService {

    private final InstallmentRepository repo;

    public InstallmentService(InstallmentRepository repo) {
        this.repo = repo;
    }

    public void save(Installment installment) {
        repo.save(installment);
    }

    public List<Installment> getAll() {
        return repo.findAll();
    }

    public Installment getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
    public Installment saveAndReturn(Installment installment) {
        return repo.save(installment);
    }
    
}
