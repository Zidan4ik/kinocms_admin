package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Hall;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface HallService {
    void save(Hall hall, MultipartFile schemaMF, MultipartFile bannerMF, List<MultipartFile> galleriesMF);

    void deleteById(Long id);

    List<Hall> getAll();

    Optional<Hall> getById(Long id);

    List<Hall> getAllByCinema(Cinema cinema);
    void deleteAllByCinema(Cinema cinema);
}
