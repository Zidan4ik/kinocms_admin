package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BannerImageService {
    void save(BannerImage bannerImage);

    void saveFile(BannerImage bannerImage, MultipartFile file);

    void saveFiles(List<BannerImage> bannersImages, List<MultipartFile> files);

    void deleteById(Long id);

    List<BannerImage> getAll();
    List<BannerImage> getAllByBanner(Banner banner);

    Optional<BannerImage> getById(Long id);
}
