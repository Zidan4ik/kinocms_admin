package com.example.kinocms_admin.service;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.LanguageCode;

import java.util.Optional;

public interface PageTranslationService {
    void save(PageTranslation page, Film film, LanguageCode code);

    void deleteById(long id);
    Optional<PageTranslation> getById(long id);
}
