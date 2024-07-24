package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    void save(List<Genre> genre);

    void deleteById(long id);

    Set<Genre> getAllByFilms(List<Film> films);

    Set<Genre> getAllByFilm(Film film);
}
