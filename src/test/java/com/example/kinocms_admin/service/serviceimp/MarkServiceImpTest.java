package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.repository.MarkRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MarkServiceImpTest {
    @Mock
    private MarkRepository markRepository;
    @InjectMocks
    private MarkServiceImp markServiceImp;
    private List<Mark> loadedMarks;
    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        loadedMarks = TestDataUtil.loadMarks();
    }

    @Test
    void saveAll() {
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        markServiceImp.saveAll(marksInSet);
        verify(markRepository, times(1)).saveAll(marksInSet);
    }

    @Test
    void deleteById() {
        markServiceImp.deleteById(ID);
        verify(markRepository, times(1)).deleteById(ID);
    }

    @Test
    void deleteAllByFilms() {
        List<Film> films = List.of(new Film());
        markServiceImp.deleteAllByFilms(films);
        verify(markRepository, times(1)).deleteAllByFilms(films);
    }

    @Test
    void getAllByFilms() {
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        List<Film> expectedFilms = TestDataUtil.loadFilms();
        when(markRepository.getAllByFilms(expectedFilms)).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByFilms(expectedFilms);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByFilms(expectedFilms);
    }

    @Test
    void getAllByNews() {
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        List<New> expectedNews = TestDataUtil.loadNews();
        when(markRepository.getAllByNews(expectedNews)).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByNews(expectedNews);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByNews(expectedNews);
    }

    @Test
    void getByName() {
        String expectedName = "18+";
        Mark expectedMark = new Mark(1L, "18+");
        when(markRepository.findByName(expectedName)).thenReturn(Optional.of(expectedMark));
        Optional<Mark> markByName = markServiceImp.getByName(expectedName);
        assertTrue(markByName.isPresent(), "Mark was not found");
        assertEquals(expectedName, markByName.get().getName(), "Names should be match");
        verify(markRepository, times(1)).findByName(expectedName);
    }

    @Test
    void getAllByShares() {
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        List<Share> expectedShares = TestDataUtil.loadShares();
        when(markRepository.getAllByShares(expectedShares)).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByShares(expectedShares);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByShares(expectedShares);
    }

    @Test
    void shouldGetAllByFilm_WhenFilmIsNotNull() {
        Film film = new Film();
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        when(markRepository.getAllByFilms(Collections.singletonList(film))).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByFilm(film);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByFilms(Collections.singletonList(film));
    }

    @Test
    void shouldGetAllByFilm_WhenFilmIsNull() {
        Set<Mark> marks = markServiceImp.getAllByFilm(null);
        assertEquals(Collections.emptySet(), marks, "Collections of marks should be empty");
    }

    @Test
    void shouldGetAllByNew_WhenNewIsNotNull() {
        New new_ = new New();
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        when(markRepository.getAllByNews(Collections.singletonList(new_))).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByNew(new_);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByNews(Collections.singletonList(new_));
    }

    @Test
    void shouldGetAllByNew_WhenNewIsNull() {
        Set<Mark> marks = markServiceImp.getAllByNew(null);
        assertEquals(Collections.emptySet(), marks, "Collections of marks should be empty");
    }

    @Test
    void shouldGetAllByShare_WhenShareIsNotNull() {
        Share share = new Share();
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        when(markRepository.getAllByShares(Collections.singletonList(share))).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByShare(share);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByShares(Collections.singletonList(share));
    }

    @Test
    void shouldGetAllByShare_WhenShareIsNull() {
        Set<Mark> marks = markServiceImp.getAllByShare(null);
        assertEquals(Collections.emptySet(), marks, "Collections of marks should be empty");
    }

    @Test
    void shouldGetAllByCinema_WhenCinemaIsNotNull() {
        Cinema cinema = new Cinema();
        Set<Mark> marksInSet = new HashSet<>(loadedMarks);
        when(markRepository.getAllByCinemas(Collections.singletonList(cinema))).thenReturn(marksInSet);
        Set<Mark> marks = markServiceImp.getAllByCinema(cinema);
        assertEquals(marksInSet.size(), marks.size(), "Sizes should be match");
        verify(markRepository, times(1)).getAllByCinemas(Collections.singletonList(cinema));
    }

    @Test
    void shouldGetAllByCinema_WhenCinemaIsNull() {
        Set<Mark> marks = markServiceImp.getAllByCinema(null);
        assertEquals(Collections.emptySet(), marks, "Collections of marks should be empty");
    }
}