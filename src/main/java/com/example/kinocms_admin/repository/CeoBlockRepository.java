package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CeoBlockRepository extends JpaRepository<CeoBlock,Long> {
    Optional<CeoBlock> getByFilmAndLanguageCode(Film film, LanguageCode code);
    Optional<CeoBlock> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);
    Optional<CeoBlock> getByHallAndLanguageCode(Hall hall, LanguageCode code);
    Optional<CeoBlock> getByNewEntityAndLanguageCode(New newEntity, LanguageCode code);
    Optional<CeoBlock> getByShareAndLanguageCode(Share share, LanguageCode code);
    Optional<CeoBlock> getByPageAndLanguageCode(Page page, LanguageCode code);
}
