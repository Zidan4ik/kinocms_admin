package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.enums.LanguageCode;

import java.util.Optional;

public interface CeoBlockService {
    void saveFilm(CeoBlock ceoBlock, Film film, LanguageCode code);
    void saveHall(CeoBlock ceoBlock, Hall hall, LanguageCode code);
    void saveCinema(CeoBlock ceoBlock, Cinema cinema, LanguageCode code);

    void deleteById(long id);

    Optional<CeoBlock> getById(long id);

    Optional<CeoBlock> getByFilmAndLanguageBlock(Film film, LanguageCode code);

    Optional<CeoBlock> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);
    Optional<CeoBlock> getByHallAndLanguageCode(Hall hall, LanguageCode code);
}
