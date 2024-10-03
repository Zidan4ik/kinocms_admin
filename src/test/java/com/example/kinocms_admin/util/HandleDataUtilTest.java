package com.example.kinocms_admin.util;

import com.example.kinocms_admin.entity.Genre;
import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.service.serviceimp.GenreServiceImp;
import com.example.kinocms_admin.service.serviceimp.MarkServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HandleDataUtilTest {
    @Mock
    private GenreServiceImp genreServiceImp;
    @Mock
    private MarkServiceImp markServiceImp;

    @Test
    void shouldSubstringMark_WithTrueOperators() {
        String mark = "[{name:'A'}]";
        String result = HandleDataUtil.substringMark(mark);
        assertEquals("A", result);
    }

    @Test
    void shouldSubstringMark_WithFalseOperators() {
        String mark = "name:'A'";
        String result = HandleDataUtil.substringMark(mark);
        assertEquals("A", result);
    }

    @Test
    void shouldFindSimilarGenre() {
        Set<Genre> genreSet = Set.of(new Genre("mystic"), new Genre("horror"));
        List<Genre> genres = genreSet.stream().toList();
        Mockito.when(genreServiceImp.getByName("mystic")).thenReturn(Optional.of(genres.get(0)));
        Mockito.when(genreServiceImp.getByName("horror")).thenReturn(Optional.empty());
        HandleDataUtil.findSimilarGenre(genreSet, genreServiceImp);
    }

    @Test
    void shouldFindSimilarMark() {
        Set<Mark> markSet = Set.of(new Mark("18+"), new Mark("16+"));
        List<Mark> marks = markSet.stream().toList();
        Mockito.when(markServiceImp.getByName("18+")).thenReturn(Optional.of(marks.get(0)));
        Mockito.when(markServiceImp.getByName("16+")).thenReturn(Optional.empty());
        HandleDataUtil.findSimilarMark(markSet, markServiceImp);
    }
    @Test
    void shouldFindSimilarMark_WhenCollectionIsNull() {
        Set<Mark> resMarks = HandleDataUtil.findSimilarMark(null, markServiceImp);
        assertTrue(resMarks.isEmpty(),"Collection should be null");
    }
}