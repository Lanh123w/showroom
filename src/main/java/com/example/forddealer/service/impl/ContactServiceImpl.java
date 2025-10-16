package com.example.forddealer.service.impl;

import com.example.forddealer.model.Contact;
import com.example.forddealer.repository.ContactRepository;
import com.example.forddealer.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
