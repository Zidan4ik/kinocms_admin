package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.repository.FilmRepository;
import com.example.kinocms_admin.util.HandleDataUtil;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceImpTest {
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private GenreServiceImp genreServiceImp;
    @Mock
    private MarkServiceImp markServiceImp;
    @InjectMocks
    private FilmServiceImp filmServiceImp;

    private List<Film> loadedFilms;
    private MultipartFile file;
    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        loadedFilms = TestDataUtil.loadFilms();
    }

    @Test
    void shouldSave() {
        Film film = new Film();
        filmServiceImp.save(film);
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void shouldSaveFilm_WhenFilmAndFilesAreNotNull() {
        Film film = new Film();
        film.setId(1L);
        file = new MockMultipartFile("fileImage1", "image1.html", "text/html", "content".getBytes());
        when(filmRepository.findById(ID)).thenReturn(Optional.of(loadedFilms.get(0)));

        try (MockedStatic<HandleDataUtil> mockedStatic = mockStatic(HandleDataUtil.class)) {
            mockedStatic.when(() -> HandleDataUtil.findSimilarGenre(film.getGenresList(), genreServiceImp))
                    .thenReturn(Set.of(new Genre("18+")));
            mockedStatic.when(() -> HandleDataUtil.findSimilarMark(film.getMarksList(), markServiceImp))
                    .thenReturn(Set.of(new Mark("mystic")));
            filmServiceImp.saveFilm(film, file, Arrays.asList(file, null));
        }
    }

    @Test
    void shouldSaveFilm_WhenThrowExceptionIO() {
        Film film = new Film();
        film.setId(1L);
        file = new MockMultipartFile("fileImage1", "image1.html", "text/html", "content".getBytes());
        when(filmRepository.findById(ID)).thenReturn(Optional.of(loadedFilms.get(0)));
        try (MockedStatic<HandleDataUtil> mockedStatic = mockStatic(HandleDataUtil.class);
             MockedStatic<ImageUtil> mockedStatic2 = mockStatic(ImageUtil.class)) {
            mockedStatic2.when(() -> ImageUtil.saveAfterDelete(anyString(), any(MultipartFile.class), anyString()))
                    .thenThrow(IOException.class);
            filmServiceImp.saveFilm(film, file, Arrays.asList(file, null));
        }
    }

    @Test
    void shouldSaveFilm_WhenFilmIdAndFilesAreAreNull() {
        Film film = new Film();
        film.setId(null);
        try (MockedStatic<HandleDataUtil> mockedStatic = mockStatic(HandleDataUtil.class)) {
            filmServiceImp.saveFilm(film, null, null);
        }
    }

    @Test
    void shouldSaveFilm_WhenFileOriginalNameIsEmpty() {
        Film film = new Film();
        film.setId(null);
        file = new MockMultipartFile("fileImage1", "", "text/html", "content".getBytes());
        try (MockedStatic<HandleDataUtil> mockedStatic = mockStatic(HandleDataUtil.class)) {
            filmServiceImp.saveFilm(film, file, null);
        }
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void deleteById() {
        filmServiceImp.deleteById(ID);
        verify(filmRepository, times(1)).deleteById(ID);
    }

    @Test
    void getAll() {
        when(filmRepository.findAll()).thenReturn(loadedFilms);
        List<Film> films = filmServiceImp.getAll();
        assertEquals(loadedFilms.size(), films.size(), "Sizes should be match");
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    void getById() {
        when(filmRepository.findById(ID)).thenReturn(Optional.of(loadedFilms.get(0)));
        Optional<Film> filmById = filmServiceImp.getById(ID);
        assertTrue(filmById.isPresent(), "Film was not found");
        assertEquals(ID, filmById.get().getId(), "Ids should be match");
        verify(filmRepository, times(1)).findById(ID);
    }

    @Test
    void shouldFindFilm_WithActiveView() {
        boolean expectedIsActive = true;
        when(filmRepository.findAll()).thenReturn(loadedFilms);
        filmServiceImp.findFilmsIsActive(expectedIsActive);
        verify(filmRepository,times(1)).findAll();
    }
    @Test
    void shouldFindFilm_WithUnActiveView() {
        boolean expectedIsActive = false;
        when(filmRepository.findAll()).thenReturn(loadedFilms);
        filmServiceImp.findFilmsIsActive(expectedIsActive);
        verify(filmRepository,times(1)).findAll();
    }
}