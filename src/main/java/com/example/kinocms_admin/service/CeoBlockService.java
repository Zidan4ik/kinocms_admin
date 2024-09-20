package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;

import java.util.Optional;

public interface CeoBlockService {
    void saveFilm(CeoBlock ceoBlock, Film film, LanguageCode code);

    void saveHall(CeoBlock ceoBlock, Hall hall, LanguageCode code);

    void saveCinema(CeoBlock ceoBlock, Cinema cinema, LanguageCode code);

    void saveNew(CeoBlock ceoBlock, New newCinema, LanguageCode code);

    void saveShare(CeoBlock ceoBlock, Share share, LanguageCode code);

    void savePage(CeoBlock ceoBlock, Page page, LanguageCode code);

    void deleteById(long id);

    void deleteAllByFilm(Film film);

    Optional<CeoBlock> getById(long id);

    Optional<CeoBlock> getByFilmAndLanguageCode(Film film, LanguageCode code);

    Optional<CeoBlock> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);

    Optional<CeoBlock> getByHallAndLanguageCode(Hall hall, LanguageCode code);

    Optional<CeoBlock> getByNewEntityAndLanguageCode(New newEntity, LanguageCode code);

    Optional<CeoBlock> getByShareAndLanguageCode(Share share, LanguageCode code);

    Optional<CeoBlock> getByPageAndLanguageCode(Page page, LanguageCode code);
}
