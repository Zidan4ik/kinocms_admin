package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    void save(Gallery galleries, MultipartFile file);
    void delete(Long id);
    List<Gallery> getAllByFilm(Film film);
    List<Gallery> getAllByCinema(Cinema cinema);
    List<Gallery> getAllByHall(Hall hall);
    List<Gallery> getAllByPage(Page page);
}
