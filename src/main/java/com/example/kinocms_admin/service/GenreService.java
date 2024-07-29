package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreService {

    void deleteById(long id);

    Set<Genre> getAllByFilms(List<Film> films);

    Set<Genre> getAllByFilm(Film film);

    Optional<Genre> getByName(String name);
}
