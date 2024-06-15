package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Gallery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    void save(Gallery galleries, MultipartFile file);
    void delete(long id);
}
