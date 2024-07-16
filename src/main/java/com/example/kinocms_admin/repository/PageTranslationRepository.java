package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageTranslationRepository extends JpaRepository<PageTranslation,Long> {
    Optional<PageTranslation> findByFilmAndAndLanguageCode(Film film, LanguageCode code);
    Optional<PageTranslation> findByCinemaAndLanguageCode(Cinema film, LanguageCode code);
}
