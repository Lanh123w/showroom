package com.example.forddealer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forddealer.model.Installment;

public interface InstallmentRepository extends JpaRepository<Installment, Integer> {
}
