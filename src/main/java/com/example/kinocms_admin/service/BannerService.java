package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Banner;

import java.util.List;
import java.util.Optional;

public interface BannerService {
    void save(Banner banner);
    void delete(Long id);
    Optional<Banner> getById(Long id);
    List<Banner> getAll();
}
