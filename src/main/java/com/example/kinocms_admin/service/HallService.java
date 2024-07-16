package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Hall;

import java.util.List;
import java.util.Optional;

public interface HallService {
    void save(Hall hall);
    void deleteById(Long id);
    List<Hall> getAll();
    Optional<Hall> getById(Long id);
}
