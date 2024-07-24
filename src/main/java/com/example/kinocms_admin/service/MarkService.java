package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Mark;

import java.util.List;
import java.util.Set;

public interface MarkService {
    void save(Set<Mark> mark);

    void deleteById(long id);

    Set<Mark> getAllByCinema(Cinema cinema);

    Set<Mark> getAllByFilm(Film film);

    Set<Mark> getAllByFilms(List<Film> films);

}
