package com.example.forddealer.service;

import java.util.List;

import com.example.forddealer.model.TestDriveRequest;

public interface TestDriveService {
    void save(TestDriveRequest request);
    List<TestDriveRequest> getAll();
    TestDriveRequest getById(Long id);
    void update(TestDriveRequest request);
}
