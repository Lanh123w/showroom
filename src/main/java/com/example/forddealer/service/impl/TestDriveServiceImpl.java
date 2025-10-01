package com.example.forddealer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forddealer.model.TestDriveRequest;
import com.example.forddealer.repository.TestDriveRepository;
import com.example.forddealer.service.TestDriveService;

@Service
public class TestDriveServiceImpl implements TestDriveService {

    @Autowired
    private TestDriveRepository testDriveRepository;

    @Override
    public void save(TestDriveRequest request) {
        testDriveRepository.save(request);
    }

    @Override
    public List<TestDriveRequest> getAll() {
        return testDriveRepository.findAll();
    }

    @Override
    public TestDriveRequest getById(Long id) {
        return testDriveRepository.findById(id).orElse(null);
    }

    @Override
    public void update(TestDriveRequest request) {
        testDriveRepository.save(request);
    }
}
