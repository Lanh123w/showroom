package com.example.forddealer.service;

import com.example.forddealer.model.Contact;
import java.util.List;

public interface ContactService {
    List<Contact> getAll();
    Contact getById(Long id);
    void delete(Long id);
}
