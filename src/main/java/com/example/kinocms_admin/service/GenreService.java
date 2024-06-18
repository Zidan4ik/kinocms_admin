package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import java.util.List;

public interface GenreService {
    void save(Genre genre);
    void deleteById(long id);
    List<Genre> getAllByFilm(Film film);
}
