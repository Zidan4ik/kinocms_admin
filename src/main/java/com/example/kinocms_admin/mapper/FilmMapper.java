package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.model.FilmDTOAdd;
import com.example.kinocms_admin.model.FilmsDTOView;
import com.example.kinocms_admin.util.MonthTranslator;
import com.example.kinocms_admin.util.SubstringUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmMapper {
    public static List<FilmsDTOView> toDTOViewFilms(List<Film> filmsEntity) {
        List<FilmsDTOView> dtoList = new ArrayList<>();
        for (Film film : filmsEntity) {
            FilmsDTOView dto = new FilmsDTOView();
            dto.setId(film.getId());
//            dto.setTitle(film.title());
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
        film.setUrlCEO(dto.getUrlCeo());
        if (!dto.getDateStart().isEmpty()) {
            film.setDateStart(LocalDate.parse(dto.getDateStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (!dto.getDateEnd().isEmpty()) {
            film.setDateEnd(LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        film.setLinkTrailer(dto.getLinkTrailer());
        if (!dto.getDurationTime().isEmpty()) {
            film.setTime(LocalTime.parse(dto.getDurationTime()));
        }
        if (!dto.getYear().isEmpty()) {
            film.setYear(Integer.parseInt(dto.getYear()));
        }
        film.setBudget(dto.getBudget());

        List<CeoBlock> ceoBlocks = new ArrayList<>();
        CeoBlock ceoBlockUkr = new CeoBlock(
                LanguageCode.Ukr,
                PageType.film,
                dto.getTitleCeoUkr(),
                dto.getKeywordsCeoUkr(),
                dto.getDescriptionCeoUkr(),
                film
        );
        ceoBlocks.add(ceoBlockUkr);
        CeoBlock ceoBlockEng = new CeoBlock(
                LanguageCode.Eng,
                PageType.film,
                dto.getTitleCeoEng(),
                dto.getKeywordsCeoEng(),
                dto.getDescriptionCeoEng(),
                film
        );
        ceoBlocks.add(ceoBlockEng);

        List<PageTranslation> pageTranslations = new ArrayList<>();
        PageTranslation pageTranslationUkr = new PageTranslation(
                LanguageCode.Ukr,
                PageType.film,
                dto.getTitleFilmUkr(),
                dto.getDescriptionFilmUkr(),
                null,
                film
        );
        pageTranslations.add(pageTranslationUkr);
        PageTranslation pageTranslationEng = new PageTranslation(
                LanguageCode.Eng,
                PageType.film,
                dto.getTitleFilmEng(),
                dto.getDescriptionFilmEng(),
                null,
                film
        );
        pageTranslations.add(pageTranslationEng);

        Set<Mark> marksResult = new HashSet<>();
        for (String s : dto.getMarks()) {
            marksResult.add(new Mark(SubstringUtil.substringMark(s)));
        }

        Set<Genre> genresResult = new HashSet<>();
        for (String genre : dto.getGenres()) {
            genresResult.add(new Genre(genre));
        }

        film.setGenresList(genresResult);
        film.setMarksList(marksResult);

        return new FilmUnifier(film, ceoBlockEng, ceoBlockUkr, pageTranslationEng, pageTranslationUkr);
    }

    public static FilmDTOAdd toDTOAdd(FilmUnifier unifier) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        FilmDTOAdd dto = new FilmDTOAdd();
        dto.setId(unifier.getFilm().getId());
        dto.setUrlCeo(unifier.getFilm().getUrlCEO());
        dto.setImage(unifier.getFilm().getPathImage());
        dto.setLinkTrailer(unifier.getFilm().getLinkTrailer());

        LocalDate dateStart = LocalDate.parse(String.valueOf(unifier.getFilm().getDateStart()), inputFormatter);
        LocalDate dateEnd = LocalDate.parse(String.valueOf(unifier.getFilm().getDateEnd()), inputFormatter);

        dto.setDateStart(dateStart.format(outputFormatter));
        dto.setDateEnd(dateEnd.format(outputFormatter));

        if (unifier.getFilm().getTime() != null) {
            dto.setDurationTime(unifier.getFilm().getTime().toString());
        }
        dto.setYear(String.valueOf(unifier.getFilm().getYear()));
        dto.setBudget(unifier.getFilm().getBudget());

        if (unifier.getPageTranslationUkr() != null) {
            dto.setTitleFilmUkr(unifier.getPageTranslationUkr().getTitle());
            dto.setDescriptionFilmUkr(unifier.getPageTranslationUkr().getDescription());
        }
        if (unifier.getPageTranslationEng() != null) {
            dto.setTitleFilmEng(unifier.getPageTranslationEng().getTitle());
            dto.setDescriptionFilmEng(unifier.getPageTranslationEng().getDescription());
        }

        if (unifier.getCeoBlockUkr() != null) {
            dto.setTitleCeoUkr(unifier.getCeoBlockUkr().getTitle());
            dto.setKeywordsCeoUkr(unifier.getCeoBlockUkr().getKeywords());
            dto.setDescriptionCeoUkr(unifier.getCeoBlockUkr().getDescriptions());
        }

        if (unifier.getCeoBlockEng() != null) {
            dto.setTitleCeoEng(unifier.getCeoBlockEng().getTitle());
            dto.setKeywordsCeoEng(unifier.getCeoBlockEng().getKeywords());
            dto.setDescriptionCeoEng(unifier.getCeoBlockEng().getDescriptions());
        }
        Set<String> marks = new HashSet<>();
        for (Mark mark : unifier.getMarks()) {
            marks.add(mark.getName());
        }
        dto.setMarks(marks);

        Set<String> genres = new HashSet<>();
        for (Genre genre : unifier.getGenres()) {
            genres.add(genre.getName());
        }
        dto.setGenres(genres);

        List<Gallery> galleries = new ArrayList<>();
        for (Gallery g : unifier.getGalleries()) {
            galleries.add(new Gallery(g.getId(),g.getLinkImage(),g.getType()));
        }
        dto.setGalleries(galleries);
        return dto;
    }
}
