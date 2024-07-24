package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.LanguageCode;

import java.util.Optional;

public interface PageTranslationService {
    void saveFilm(PageTranslation page, Film film, LanguageCode code);
    void saveCinema(PageTranslation page, Cinema cinema, LanguageCode code);
    void saveHall(PageTranslation page, Hall hall, LanguageCode code);

    void deleteById(long id);

    Optional<PageTranslation> getById(long id);

    Optional<PageTranslation> getByFilmAndLanguageCode(Film film, LanguageCode code);

    Optional<PageTranslation> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);

    Optional<PageTranslation> getByHallAndLanguageCode(Hall hall, LanguageCode code);
    PageTranslation getAllByFilmAndLanguageCode(Film film, LanguageCode code);
}
