package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.New;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface NewService {
    void save(New newEntity);
    void saveNew(New newEntity, MultipartFile fileImage,List<MultipartFile> galleriesMF);
    void deleteById(Long id);
    List<New> getAll();
    Optional<New> getById(Long id);
}
