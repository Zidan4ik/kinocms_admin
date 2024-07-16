package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.enums.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CeoBlockRepository extends JpaRepository<CeoBlock,Long> {
    Optional<CeoBlock> getByFilmAndLanguageCode(Film film, LanguageCode code);
    Optional<CeoBlock> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);
}
