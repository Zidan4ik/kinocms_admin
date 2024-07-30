package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Contact;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    void save(Contact contact);
    void saveImages(Contact contact, MultipartFile fileLogo);
    Optional<Contact> getById(Long id);
    void delete(Long id);
    List<Contact> getAll();
}
