package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.PageTranslationRepository;
import com.example.kinocms_admin.service.PageTranslationService;
import lombok.RequiredArgsConstructor;
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
    public void saveNew(PageTranslation page, New newEntity, LanguageCode code) {
        Optional<PageTranslation> translator = getByNewAndLanguageCode(newEntity, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
    }

    @Override
    public void saveShare(PageTranslation page, Share share, LanguageCode code) {
        Optional<PageTranslation> translator = getByShareAndLanguageCode(share, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
    }

    @Override
    public void savePage(PageTranslation page, Page pageEntity, LanguageCode code) {
        Optional<PageTranslation> translator = getByPageAndLanguageCode(pageEntity, code);
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
    public Optional<PageTranslation> getByNewAndLanguageCode(New newEntity, LanguageCode code) {
        return pageTranslationRepository.findByNewEntityAndLanguageCode(newEntity,code);
    }

    @Override
    public Optional<PageTranslation> getByShareAndLanguageCode(Share share, LanguageCode code) {
        return pageTranslationRepository.findByShareAndLanguageCode(share,code);
    }

    @Override
    public Optional<PageTranslation> getByPageAndLanguageCode(Page page, LanguageCode code) {
        return pageTranslationRepository.findByPageAndLanguageCode(page,code);
    }
}
