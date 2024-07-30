package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Share;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ShareService {
    void save(Share share);

    void saveShare(Share share, MultipartFile fileImage, MultipartFile fileBanner);

    void deleteById(Long id);

    List<Share> getAll();

    Optional<Share> getById(Long id);
}
