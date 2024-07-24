package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.PageTranslationRepository;
import com.example.kinocms_admin.service.PageTranslationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageTranslationServiceImp implements PageTranslationService {
    private final PageTranslationRepository pageTranslationRepository;

    @Override
    public void saveFilm(PageTranslation page, Film film, LanguageCode code) {
        Optional<PageTranslation> translator = getByFilmAndLanguageCode(film, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
    }

    @Override
    public void saveCinema(PageTranslation page, Cinema cinema, LanguageCode code) {
        Optional<PageTranslation> translator = getByCinemaAndLanguageCode(cinema, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
    }

    @Override
    public void saveHall(PageTranslation page, Hall hall, LanguageCode code) {
        Optional<PageTranslation> translator = getByHallAndLanguageCode(hall, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
    }

    @Override
    public void deleteById(long id) {
        pageTranslationRepository.deleteById(id);
    }

    @Override
    public Optional<PageTranslation> getById(long id) {
        return pageTranslationRepository.findById(id);
    }

    @Override
    public Optional<PageTranslation> getByFilmAndLanguageCode(Film film, LanguageCode code) {
        return pageTranslationRepository.findByFilmAndAndLanguageCode(film, code);
    }
    @Override
    public Optional<PageTranslation> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code) {
        return pageTranslationRepository.findByCinemaAndLanguageCode(cinema, code);
    }

    @Override
    public Optional<PageTranslation> getByHallAndLanguageCode(Hall hall, LanguageCode code) {
        return pageTranslationRepository.findByHallAndLanguageCode(hall,code);
    }

    @Override
    public PageTranslation getAllByFilmAndLanguageCode(Film film, LanguageCode code) {
        Optional<PageTranslation> translationBD = pageTranslationRepository.findByFilmAndAndLanguageCode(film, code);
        if (translationBD.isPresent()) {
            return translationBD.get();
        } else {
            return null;
        }
    }
}
