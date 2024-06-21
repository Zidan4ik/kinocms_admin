package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.model.FilmDTOAdd;
import com.example.kinocms_admin.model.FilmsDTOView;
import com.example.kinocms_admin.util.MonthTranslator;
import com.example.kinocms_admin.util.SubstringUtil;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.SimpleFormatter;

@Service
public class FilmMapper {
    public static List<FilmsDTOView> toDTOViewFilms(List<Film> filmsEntity) {
        List<FilmsDTOView> dtoList = new ArrayList<>();
        for (Film film : filmsEntity) {
            FilmsDTOView dto = new FilmsDTOView();
            dto.setId(film.getId());
            dto.setTitle(film.getTitle());
            dto.setPathImage(film.getPathImage());
            String date = film.getDateStart().getDayOfMonth() + " " + MonthTranslator.translate(film.getDateStart().getMonth()) + " - "
                    + film.getDateEnd().getDayOfMonth() + " " + MonthTranslator.translate(film.getDateEnd().getMonth());
            dto.setDate(date);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static FilmUnifier toEntityAdd(FilmDTOAdd dto) {
        Film film = new Film();
        film.setId(dto.getId());
        film.setTitle(dto.getTitleFilm());
        if(!dto.getDateStart().isEmpty()){
            film.setDateStart(LocalDate.parse(dto.getDateStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if(!dto.getDateEnd().isEmpty()){
            film.setDateEnd(LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        film.setDescription(dto.getDescriptionFilm());
        film.setLinkTrailer(dto.getLinkTrailer());
        if(!dto.getDurationTime().isEmpty()){
            film.setTime(LocalTime.parse(dto.getDurationTime()));
        }
        if(!dto.getYear().isEmpty()){
            film.setYear(Integer.parseInt(dto.getYear()));
        }
        film.setBudget(dto.getBudget());

        CeoBlock ceoBlock = new CeoBlock();
        ceoBlock.setId(dto.getId());
        ceoBlock.setTitle(dto.getTitleCeo());
        ceoBlock.setUrl(dto.getUrlCeo());
        ceoBlock.setKeywords(dto.getKeywordsCeo());
        ceoBlock.setDescriptions(dto.getDescriptionCeo());


        Set<Mark> marksResult = new HashSet<>();

        for (String s:dto.getMarks()){
            marksResult.add(new Mark(SubstringUtil.substringMark(s)));
        }

        Set<Genre> genresResult = new HashSet<>();
//        List<Genre> genresResult = new ArrayList<>();
        for (String genre:dto.getGenres()){
            genresResult.add(new Genre(genre));
        }
        return new FilmUnifier(film,marksResult,genresResult,null,ceoBlock);
    }

    public static FilmDTOAdd toDTOAdd(FilmUnifier unifier) {
        FilmDTOAdd dto = new FilmDTOAdd();

//        Film film = unifier.getFilm();
//        Mark mark = unifier.getMark();
//        Genre genre = unifier.getGenre();
//        Gallery gallery = unifier.getGallery();
//        CeoBlock ceoBlock = unifier.getCeoBlock();

//        dto.setId(film.getId());
//        dto.setTitleFilm(film.getTitle());
//        dto.setDateStart(film.getDateStart().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));

        return dto;
    }
}
