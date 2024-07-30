package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MarkService {
    void save(Set<Mark> mark);

    void deleteById(long id);

    Set<Mark> getAllByCinema(Cinema cinema);

    Set<Mark> getAllByFilm(Film film);
    Set<Mark> getAllByNew(New newEntity);
    Set<Mark> getAllByNew(Share share);

    Set<Mark> getAllByFilms(List<Film> films);
    Set<Mark> getAllByNews(List<New> news);
    Set<Mark> getAllByShares(List<Share> news);
    Optional<Mark> getByName(String name);

}
