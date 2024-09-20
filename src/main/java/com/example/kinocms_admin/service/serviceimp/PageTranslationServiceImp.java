package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.PageTranslationRepository;
import com.example.kinocms_admin.service.PageTranslationService;
import com.example.kinocms_admin.util.LogUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageTranslationServiceImp implements PageTranslationService {
    private final PageTranslationRepository pageTranslationRepository;

    @Override
    public void saveFilm(PageTranslation page, Film film, LanguageCode code) {
        LogUtil.logSaveNotification("translator", "id", page.getId());
        Optional<PageTranslation> translator = getByFilmAndLanguageCode(film, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
        LogUtil.logSaveInfo("Translator", "id", page.getId());
    }

    @Override
    public void saveCinema(PageTranslation page, Cinema cinema, LanguageCode code) {
        LogUtil.logSaveNotification("translator", "id", page.getId());
        Optional<PageTranslation> translator = getByCinemaAndLanguageCode(cinema, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
        LogUtil.logSaveInfo("Translator", "id", page.getId());
    }

    @Override
    public void saveHall(PageTranslation page, Hall hall, LanguageCode code) {
        LogUtil.logSaveNotification("translator", "id", page.getId());
        Optional<PageTranslation> translator = getByHallAndLanguageCode(hall, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
        LogUtil.logSaveInfo("Translator", "id", page.getId());
    }

    @Override
    public void saveNew(PageTranslation page, New newEntity, LanguageCode code) {
        LogUtil.logSaveNotification("translator", "id", page.getId());
        Optional<PageTranslation> translator = getByNewAndLanguageCode(newEntity, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
        LogUtil.logSaveInfo("Translator", "id", page.getId());
    }

    @Override
    public void saveShare(PageTranslation page, Share share, LanguageCode code) {
        LogUtil.logSaveNotification("translator", "id", page.getId());
        Optional<PageTranslation> translator = getByShareAndLanguageCode(share, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
        LogUtil.logSaveInfo("Translator", "id", page.getId());

    }

    @Override
    public void savePage(PageTranslation page, Page pageEntity, LanguageCode code) {
        LogUtil.logSaveNotification("translator", "id", page.getId());
        Optional<PageTranslation> translator = getByPageAndLanguageCode(pageEntity, code);
        translator.ifPresent(pageTranslation -> page.setId(pageTranslation.getId()));
        pageTranslationRepository.save(page);
        LogUtil.logSaveInfo("Translator", "id", page.getId());
    }

    @Override
    public void deleteById(long id) {
        LogUtil.logDeleteNotification("translator", "id", id);
        pageTranslationRepository.deleteById(id);
        LogUtil.logDeleteInfo("Translator", "id", id);
    }

    @Override
    @Transactional
    public void deleteAllByFilm(Film film) {
        LogUtil.logDeleteAllNotification("translator");
        pageTranslationRepository.deleteAllByFilm(film);
        LogUtil.logDeleteAllInfo("Translator", "film");
    }

    @Override
    public Optional<PageTranslation> getById(long id) {
        LogUtil.logDeleteAllNotification("pages");
        Optional<PageTranslation> pageId = pageTranslationRepository.findById(id);
        LogUtil.logDeleteAllInfo("Pages", "id");
        return pageId;
    }

    @Override
    public Optional<PageTranslation> getByFilmAndLanguageCode(Film film, LanguageCode code) {
        LogUtil.logGetNotification("translator", "film", "language", film, code);
        Optional<PageTranslation> pageByFilmAndCode = pageTranslationRepository.findByFilmAndAndLanguageCode(film, code);
        LogUtil.logGetInfo("Translator Film", "film", "language", film, code, pageByFilmAndCode.isPresent());
        return pageByFilmAndCode;
    }

    @Override
    public Optional<PageTranslation> getByCinemaAndLanguageCode(Cinema cinema, LanguageCode code) {
        LogUtil.logGetNotification("translator", "cinema", "language", cinema, code);
        Optional<PageTranslation> pageByCinemaAndCode = pageTranslationRepository.findByCinemaAndLanguageCode(cinema, code);
        LogUtil.logGetInfo("Translator Cinema", "cinema", "language", cinema, code, pageByCinemaAndCode.isPresent());
        return pageByCinemaAndCode;
    }

    @Override
    public Optional<PageTranslation> getByHallAndLanguageCode(Hall hall, LanguageCode code) {
        LogUtil.logGetNotification("translator", "hall", "language", hall, code);
        Optional<PageTranslation> pageByHallAndCode = pageTranslationRepository.findByHallAndLanguageCode(hall, code);
        LogUtil.logGetInfo("Translator Hall", "hall", "language", hall, code, pageByHallAndCode.isPresent());
        return pageByHallAndCode;

    }

    @Override
    public Optional<PageTranslation> getByNewAndLanguageCode(New newEntity, LanguageCode code) {
        LogUtil.logGetNotification("translator", "new", "language", newEntity, code);
        Optional<PageTranslation> pageByNewAndCode = pageTranslationRepository.findByNewEntityAndLanguageCode(newEntity, code);
        LogUtil.logGetInfo("Translator New", "new", "language", newEntity, code, pageByNewAndCode.isPresent());
        return pageByNewAndCode;
    }

    @Override
    public Optional<PageTranslation> getByShareAndLanguageCode(Share share, LanguageCode code) {
        LogUtil.logGetNotification("translator", "share", "language", share, code);
        Optional<PageTranslation> pageByShareAndCode = pageTranslationRepository.findByShareAndLanguageCode(share, code);
        LogUtil.logGetInfo("Translator Share", "share", "language", share, code, pageByShareAndCode.isPresent());
        return pageByShareAndCode;
    }

    @Override
    public Optional<PageTranslation> getByPageAndLanguageCode(Page page, LanguageCode code) {
        LogUtil.logGetNotification("translator", "page", "language", page, code);
        Optional<PageTranslation> pageByPageAndCode = pageTranslationRepository.findByPageAndLanguageCode(page, code);
        LogUtil.logGetInfo("Translator Additional Page", "page", "language", page, code, pageByPageAndCode.isPresent());
        return pageByPageAndCode;
    }
}
