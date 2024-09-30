package com.example.kinocms_admin.service.serviceimp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.PageTranslationRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PageTranslationServiceImpTest {
    @Mock
    private PageTranslationRepository translatorRepository;
    @InjectMocks
    private PageTranslationServiceImp translatorService;
    private static final Long ID = 1L;
    private List<PageTranslation> loadedTranslators;

    @BeforeEach
    void setUp() {
        loadedTranslators = TestDataUtil.loadTranslators();
    }

    @Test
    void saveFilm() {
        PageTranslation translation = loadedTranslators.get(0);
        Film film = new Film();
        when(translatorRepository.findByFilmAndAndLanguageCode(film,LanguageCode.Ukr)).thenReturn(Optional.of(translation));
        translatorService.saveFilm(translation,film, LanguageCode.Ukr);
        verify(translatorRepository,times(1)).save(translation);
        verify(translatorRepository,times(1)).findByFilmAndAndLanguageCode(film,LanguageCode.Ukr);
    }

    @Test
    void saveCinema() {
        PageTranslation translation = loadedTranslators.get(0);
        Cinema cinema = new Cinema();
        when(translatorRepository.findByCinemaAndLanguageCode(cinema,LanguageCode.Ukr)).thenReturn(Optional.of(translation));
        translatorService.saveCinema(translation,cinema, LanguageCode.Ukr);
        verify(translatorRepository,times(1)).save(translation);
        verify(translatorRepository,times(1)).findByCinemaAndLanguageCode(cinema,LanguageCode.Ukr);
    }

    @Test
    void saveHall() {
        PageTranslation translation = loadedTranslators.get(0);
        Hall hall = new Hall();
        when(translatorRepository.findByHallAndLanguageCode(hall,LanguageCode.Ukr)).thenReturn(Optional.of(translation));
        translatorService.saveHall(translation,hall, LanguageCode.Ukr);
        verify(translatorRepository,times(1)).save(translation);
        verify(translatorRepository,times(1)).findByHallAndLanguageCode(hall,LanguageCode.Ukr);
    }

    @Test
    void saveNew() {
        PageTranslation translation = loadedTranslators.get(0);
        New newEntity = new New();
        when(translatorRepository.findByNewEntityAndLanguageCode(newEntity,LanguageCode.Ukr)).thenReturn(Optional.of(translation));
        translatorService.saveNew(translation,newEntity, LanguageCode.Ukr);
        verify(translatorRepository,times(1)).save(translation);
        verify(translatorRepository,times(1)).findByNewEntityAndLanguageCode(newEntity,LanguageCode.Ukr);
    }

    @Test
    void saveShare() {
        PageTranslation translation = loadedTranslators.get(0);
        Share share = new Share();
        when(translatorRepository.findByShareAndLanguageCode(share,LanguageCode.Ukr)).thenReturn(Optional.of(translation));
        translatorService.saveShare(translation,share, LanguageCode.Ukr);
        verify(translatorRepository,times(1)).save(translation);
        verify(translatorRepository,times(1)).findByShareAndLanguageCode(share,LanguageCode.Ukr);
    }

    @Test
    void savePage() {
        PageTranslation translation = loadedTranslators.get(0);
        Page page = new Page();
        when(translatorRepository.findByPageAndLanguageCode(page,LanguageCode.Ukr)).thenReturn(Optional.of(translation));
        translatorService.savePage(translation,page, LanguageCode.Ukr);
        verify(translatorRepository,times(1)).save(translation);
        verify(translatorRepository,times(1)).findByPageAndLanguageCode(page,LanguageCode.Ukr);
    }

    @Test
    void deleteById() {
        translatorService.deleteById(ID);
        verify(translatorRepository,times(1)).deleteById(ID);
    }

    @Test
    void deleteAllByFilm() {
        Film film = new Film();
        translatorService.deleteAllByFilm(film);
        verify(translatorRepository,times(1)).deleteAllByFilm(film);
    }

    @Test
    void getById() {
        when(translatorRepository.findById(ID)).thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translatorById = translatorService.getById(ID);
        assertTrue(translatorById.isPresent(),"Translator was not found");
        assertEquals(ID,translatorById.get().getId(),"Ids should be match");
        verify(translatorRepository,times(1)).findById(ID);
    }

    @Test
    void getByFilmAndLanguageCode() {
        Film expectedFilm = new Film();
        loadedTranslators.get(0).setFilm(expectedFilm);
        LanguageCode expectedLanguage = LanguageCode.Ukr;
        when(translatorRepository.findByFilmAndAndLanguageCode(expectedFilm, expectedLanguage))
                .thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translator = translatorService.getByFilmAndLanguageCode(expectedFilm, expectedLanguage);
        assertTrue(translator.isPresent(),"Translator was not found");
        assertEquals(expectedFilm,translator.get().getFilm());
        assertEquals(expectedLanguage,translator.get().getLanguageCode());
    }

    @Test
    void getByCinemaAndLanguageCode() {
        Cinema expectedCinema = new Cinema();
        loadedTranslators.get(0).setCinema(expectedCinema);
        LanguageCode expectedLanguage = LanguageCode.Ukr;
        when(translatorRepository.findByCinemaAndLanguageCode(expectedCinema, expectedLanguage))
                .thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translator = translatorService.getByCinemaAndLanguageCode(expectedCinema, expectedLanguage);
        assertTrue(translator.isPresent(),"Translator was not found");
        assertEquals(expectedCinema,translator.get().getCinema());
        assertEquals(expectedLanguage,translator.get().getLanguageCode());
    }

    @Test
    void getByHallAndLanguageCode() {
        Hall expectedHall = new Hall();
        loadedTranslators.get(0).setHall(expectedHall);
        LanguageCode expectedLanguage = LanguageCode.Ukr;
        when(translatorRepository.findByHallAndLanguageCode(expectedHall, expectedLanguage))
                .thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translator = translatorService.getByHallAndLanguageCode(expectedHall, expectedLanguage);
        assertTrue(translator.isPresent(),"Translator was not found");
        assertEquals(expectedHall,translator.get().getHall());
        assertEquals(expectedLanguage,translator.get().getLanguageCode());
    }

    @Test
    void getByNewAndLanguageCode() {
        New expectedNew = new New();
        loadedTranslators.get(0).setNewEntity(expectedNew);
        LanguageCode expectedLanguage = LanguageCode.Ukr;
        when(translatorRepository.findByNewEntityAndLanguageCode(expectedNew, expectedLanguage))
                .thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translator = translatorService.getByNewAndLanguageCode(expectedNew, expectedLanguage);
        assertTrue(translator.isPresent(),"Translator was not found");
        assertEquals(expectedNew,translator.get().getNewEntity());
        assertEquals(expectedLanguage,translator.get().getLanguageCode());
    }

    @Test
    void getByShareAndLanguageCode() {
        Share expectedShare = new Share();
        loadedTranslators.get(0).setShare(expectedShare);
        LanguageCode expectedLanguage = LanguageCode.Ukr;
        when(translatorRepository.findByShareAndLanguageCode(expectedShare, expectedLanguage))
                .thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translator = translatorService.getByShareAndLanguageCode(expectedShare, expectedLanguage);
        assertTrue(translator.isPresent(),"Translator was not found");
        assertEquals(expectedShare,translator.get().getShare());
        assertEquals(expectedLanguage,translator.get().getLanguageCode());
    }

    @Test
    void getByPageAndLanguageCode() {
        Page expectedPage = new Page();
        loadedTranslators.get(0).setPage(expectedPage);
        LanguageCode expectedLanguage = LanguageCode.Ukr;
        when(translatorRepository.findByPageAndLanguageCode(expectedPage, expectedLanguage))
                .thenReturn(Optional.of(loadedTranslators.get(0)));
        Optional<PageTranslation> translator = translatorService.getByPageAndLanguageCode(expectedPage, expectedLanguage);
        assertTrue(translator.isPresent(),"Translator was not found");
        assertEquals(expectedPage,translator.get().getPage());
        assertEquals(expectedLanguage,translator.get().getLanguageCode());
    }
}