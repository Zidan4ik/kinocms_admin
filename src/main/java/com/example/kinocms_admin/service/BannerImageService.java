package com.example.kinocms_admin.service;


import com.example.kinocms_admin.entity.BannerImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BannerImageService {
    void save(BannerImage bannerImage);

    void saveFile(BannerImage bannerImage, MultipartFile file);

    void delete(Long id);

    List<BannerImage> getAll();

    Optional<BannerImage> getById(Long id);
}
