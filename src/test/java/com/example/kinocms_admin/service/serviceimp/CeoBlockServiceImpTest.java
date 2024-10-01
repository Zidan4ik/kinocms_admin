package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.repository.CeoBlockRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CeoBlockServiceImpTest {
    @Mock
    private CeoBlockRepository ceoBlockRepository;
    @InjectMocks
    private CeoBlockServiceImp ceoBlockServiceImp;
    private final static Long ID = 1L;
    private List<CeoBlock> loadedCeoBlock;

    @BeforeEach
    void setUp() {
        loadedCeoBlock = TestDataUtil.loadCeoBlock();
    }

    @Test
    void shouldSaveFilm_WhenGetDataAreNotEmpty() {
        Film expectedFilm = new Film();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        Mockito.when(ceoBlockRepository.getByFilmAndLanguageCode(expectedFilm,expectedLanguageCode))
                .thenReturn(Optional.of(loadedCeoBlock.get(0)));
        ceoBlockServiceImp.saveFilm(loadedCeoBlock.get(0), expectedFilm,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByFilmAndLanguageCode(expectedFilm, expectedLanguageCode);
    }
    @Test
    void shouldSaveFilm_WhenGetDataAreEmpty() {
        Film expectedFilm = new Film();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        ceoBlockServiceImp.saveFilm(loadedCeoBlock.get(0), expectedFilm,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByFilmAndLanguageCode(expectedFilm, expectedLanguageCode);
    }

    @Test
    void shouldSaveHall_WhenGetDataAreNotEmpty() {
        Hall expectedHall = new Hall();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        Mockito.when(ceoBlockRepository.getByHallAndLanguageCode(expectedHall,expectedLanguageCode))
                .thenReturn(Optional.of(loadedCeoBlock.get(0)));
        ceoBlockServiceImp.saveHall(loadedCeoBlock.get(0), expectedHall,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByHallAndLanguageCode(expectedHall, expectedLanguageCode);
    }
    @Test
    void shouldSaveHall_WhenGetDataAreEmpty() {
        Hall expectedHall = new Hall();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        ceoBlockServiceImp.saveHall(loadedCeoBlock.get(0), expectedHall,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByHallAndLanguageCode(expectedHall, expectedLanguageCode);
    }


    @Test
    void shouldSaveCinema_WhenGetDataAreNotEmpty() {
        Cinema expectedCinema = new Cinema();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        Mockito.when(ceoBlockRepository.getByCinemaAndLanguageCode(expectedCinema,expectedLanguageCode))
                .thenReturn(Optional.of(loadedCeoBlock.get(0)));
        ceoBlockServiceImp.saveCinema(loadedCeoBlock.get(0), expectedCinema,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByCinemaAndLanguageCode(expectedCinema, expectedLanguageCode);

    }
    @Test
    void shouldSaveCinema_WhenGetDataAreEmpty() {
        Cinema expectedCinema = new Cinema();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        ceoBlockServiceImp.saveCinema(loadedCeoBlock.get(0), expectedCinema,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByCinemaAndLanguageCode(expectedCinema, expectedLanguageCode);

    }

    @Test
    void shouldSaveNew_WhenGetDataAreNotEmpty() {
        New expectedNew = new New();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        Mockito.when(ceoBlockRepository.getByNewEntityAndLanguageCode(expectedNew,expectedLanguageCode))
                .thenReturn(Optional.of(loadedCeoBlock.get(0)));
        ceoBlockServiceImp.saveNew(loadedCeoBlock.get(0), expectedNew,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByNewEntityAndLanguageCode(expectedNew, expectedLanguageCode);

    }
    @Test
    void shouldSaveNew_WhenGetDataAreEmpty() {
        New expectedNew = new New();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        ceoBlockServiceImp.saveNew(loadedCeoBlock.get(0), expectedNew,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByNewEntityAndLanguageCode(expectedNew, expectedLanguageCode);

    }

    @Test
    void shouldSaveShare_WhenGetDataAreNotEmpty() {
        Share expectedShare = new Share();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        Mockito.when(ceoBlockRepository.getByShareAndLanguageCode(expectedShare,expectedLanguageCode))
                .thenReturn(Optional.of(loadedCeoBlock.get(0)));
        ceoBlockServiceImp.saveShare(loadedCeoBlock.get(0), expectedShare,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByShareAndLanguageCode(expectedShare, expectedLanguageCode);

    }
    @Test
    void shouldSaveShare_WhenGetDataAreEmpty() {
        Share expectedShare = new Share();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        ceoBlockServiceImp.saveShare(loadedCeoBlock.get(0), expectedShare,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByShareAndLanguageCode(expectedShare, expectedLanguageCode);
    }

    @Test
    void shouldSavePage_WhenGetDataAreNotEmpty() {
        Page expectedPage = new Page();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        Mockito.when(ceoBlockRepository.getByPageAndLanguageCode(expectedPage,expectedLanguageCode))
                .thenReturn(Optional.of(loadedCeoBlock.get(0)));
        ceoBlockServiceImp.savePage(loadedCeoBlock.get(0), expectedPage,expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByPageAndLanguageCode(expectedPage, expectedLanguageCode);

    }
    @Test
    void shouldSavePage_WhenGetDataAreEmpty() {
        Page expectedPage = new Page();
        LanguageCode expectedLanguageCode = LanguageCode.Ukr;
        ceoBlockServiceImp.savePage(loadedCeoBlock.get(0), expectedPage, expectedLanguageCode);
        verify(ceoBlockRepository,times(1)).save(loadedCeoBlock.get(0));
        verify(ceoBlockRepository,times(1)).getByPageAndLanguageCode(expectedPage, expectedLanguageCode);
    }

    @Test
    void shouldDeleteCeoBlock_ById() {
        ceoBlockServiceImp.deleteById(ID);
        verify(ceoBlockRepository,times(1)).deleteById(ID);
    }

    @Test
    void shouldDeleteAllCeoBlocks_ByFilm() {
        Film film = new Film();
        ceoBlockServiceImp.deleteAllByFilm(film);
        verify(ceoBlockRepository,times(1)).deleteAllByFilm(film);
    }

    @Test
    void shouldGetCeoBlock_ById() {
        when(ceoBlockRepository.findById(ID)).thenReturn(Optional.of(loadedCeoBlock.get(0)));
        Optional<CeoBlock> ceoBlockById = ceoBlockServiceImp.getById(ID);
        assertTrue(ceoBlockById.isPresent(),"CeoBlock was not found");
        assertEquals(ID,ceoBlockById.get().getId(),"Ids should be match");
        verify(ceoBlockRepository,times(1)).findById(ID);
    }
}