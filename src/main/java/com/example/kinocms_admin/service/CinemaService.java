package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    void save(Cinema cinema);
    void deleteById(Long id);
    List<Cinema> getAll();
    Optional<Cinema> getById(Long id);
}
