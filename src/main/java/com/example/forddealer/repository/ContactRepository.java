package com.example.forddealer.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forddealer.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
