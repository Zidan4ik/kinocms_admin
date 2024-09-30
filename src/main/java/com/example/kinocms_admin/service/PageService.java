package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.PageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PageService {
    Page save(Page page);

    void saveImages(Page page, MultipartFile fileBanner, MultipartFile fileImage1, MultipartFile fileImage2, MultipartFile fileImage3, List<MultipartFile> galleries);

    Optional<Page> getById(Long id);

    void deleteById(Long id);

    List<Page> getAll();
    List<Page> getAllByPageType(PageType type);
    void updatePageAttributes(Page page, Optional<Page> pageById1);
}
