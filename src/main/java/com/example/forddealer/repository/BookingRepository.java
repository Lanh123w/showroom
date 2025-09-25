package com.example.forddealer.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forddealer.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
