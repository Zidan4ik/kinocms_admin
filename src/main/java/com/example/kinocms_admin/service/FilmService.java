package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Film;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    void save(Film film, MultipartFile multipartFile);
    void deleteById(long id);
    List<Film> getAll();
    Optional<Film> getById(long id);
}
