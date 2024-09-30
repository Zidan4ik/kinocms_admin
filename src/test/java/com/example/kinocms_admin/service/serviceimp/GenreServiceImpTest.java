package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.repository.GenreRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImpTest {
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreServiceImp genreServiceImp;
    private List<Genre> loadedGenres;
    private final static Long ID = 1L;
    @BeforeEach
    void setUp() {
        loadedGenres = TestDataUtil.loadGenres();
    }

    @Test
    void deleteById() {
        genreServiceImp.deleteById(ID);
        verify(genreRepository,times(1)).deleteById(ID);
    }

    @Test
    void deleteAllByFilms() {
        List<Film> films = Collections.singletonList(new Film());
        genreServiceImp.deleteAllByFilms(films);
        verify(genreRepository,times(1)).deleteAllByFilms(films);
    }

    @Test
    void getAllByFilms() {
        List<Film> films = Collections.singletonList(new Film());
        when(genreRepository.getAllByFilms(films)).thenReturn(new HashSet<>(loadedGenres));
        Set<Genre> genresByFilms = genreServiceImp.getAllByFilms(films);
        assertEquals(loadedGenres.size(),genresByFilms.size(),"Sizes should be match");
    }

    @Test
    void shouldGetAllByFilm_WhenFilmIsNotNull() {
        Film film = new Film();
        List<Film> films = Collections.singletonList(film);
        when(genreRepository.getAllByFilms(films)).thenReturn(new HashSet<>(loadedGenres));
        Set<Genre> genresByFilms = genreServiceImp.getAllByFilm(film);
        assertEquals(loadedGenres.size(),genresByFilms.size(),"Sizes should be match");
    }
    @Test
    void shouldGetAllByFilm_WhenFilmIsNull() {
        Set<Genre> genresByFilms = genreServiceImp.getAllByFilm(null);
        assertEquals(0,genresByFilms.size(),"Sizes should be 0");
    }

    @Test
    void getByName() {
        String expectedName = "18+";
        when(genreRepository.findByName(expectedName)).thenReturn(Optional.of(new Genre(expectedName)));
        Optional<Genre> genreByName = genreServiceImp.getByName(expectedName);
        assertTrue(genreByName.isPresent(),"Genre was not found");
        assertEquals(expectedName,genreByName.get().getName(),"Names should be match");
    }
}