package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;

import java.util.Optional;

public interface PageTranslationService {
    void saveFilm(PageTranslation page, Film film, LanguageCode code);
    void saveCinema(PageTranslation page, Cinema cinema, LanguageCode code);
    void saveHall(PageTranslation page, Hall hall, LanguageCode code);
    void saveNew(PageTranslation page, New newEntity, LanguageCode code);
    void saveShare(PageTranslation page, Share share, LanguageCode code);
    void savePage(PageTranslation page, Page pageEntity, LanguageCode code);

    void deleteById(long id);

    Optional<PageTranslation> getById(long id);

    Optional<PageTranslation> getByFilmAndLanguageCode(Film film, LanguageCode code);

    Optional<PageTranslation> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);

    Optional<PageTranslation> getByHallAndLanguageCode(Hall hall, LanguageCode code);
    Optional<PageTranslation> getByNewAndLanguageCode(New newEntity, LanguageCode code);
    Optional<PageTranslation> getByShareAndLanguageCode(Share share, LanguageCode code);
    Optional<PageTranslation> getByPageAndLanguageCode(Page page, LanguageCode code);

}
