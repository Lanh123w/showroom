package com.example.forddealer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forddealer.model.TestDriveRequest;

public interface TestDriveRepository extends JpaRepository<TestDriveRequest, Long> {}
