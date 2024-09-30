package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.*;

import java.util.List;

public interface GalleryService {
    void save(Gallery galleries);

    void deleteById(Long id);

    void deleteAllByFilm(Film film);

    List<Gallery> getAllByFilm(Film film);

    List<Gallery> getAllByCinema(Cinema cinema);

    List<Gallery> getAllByHall(Hall hall);

    List<Gallery> getAllByPage(Page page);
}
