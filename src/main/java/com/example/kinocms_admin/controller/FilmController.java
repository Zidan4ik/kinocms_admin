package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.model.FilmDTOAdd;
import com.example.kinocms_admin.model.FilmsDTOView;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class FilmController {
    private final FilmServiceImp filmServiceImp;
    private final GalleryServiceImp galleryServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final MarkServiceImp markServiceImp;
    private final GenreServiceImp genreServiceImp;

    @GetMapping("/films")
    public ModelAndView viewFilms() {
        ModelAndView model = new ModelAndView("films/view-films");
        List<Film> filmActive = filmServiceImp.findFilmsIsActive(true);
        List<Film> filmUnActive = filmServiceImp.findFilmsIsActive(false);
        List<FilmsDTOView> dtoFilmsActive = FilmMapper.toDTOViewFilms(filmActive);
        List<FilmsDTOView> dtoFilmsUnActive = FilmMapper.toDTOViewFilms(filmUnActive);

        model.addObject("filmToday", dtoFilmsActive);
        model.addObject("filmSoon", dtoFilmsUnActive);
        return model;
    }

    @GetMapping("/film/add")
    public ModelAndView addFilm(@ModelAttribute(name = "film") FilmDTOAdd film) {
        ModelAndView model = new ModelAndView("films/film-add");
        return model;
    }
    @GetMapping("/film/{id}/edit")
    public ModelAndView editFilm(@PathVariable(name = "id") Long id){
        ModelAndView model = new ModelAndView("films/film-edit");
        return model;
    }


    @GetMapping("/film/{id}")
    @ResponseBody
    public FilmDTOAdd getFilmData(@PathVariable(name = "id") Long id) {
        List<Film> films = new ArrayList<>();
        Optional<Film> byId = filmServiceImp.getById(id);
        Optional<PageTranslation> pageUkr = pageTranslationServiceImp.getByFilmAndLanguageCode(byId.get(), LanguageCode.Ukr);
        Optional<PageTranslation> pageEng = pageTranslationServiceImp.getByFilmAndLanguageCode(byId.get(), LanguageCode.Eng);
        Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByFilmAndLanguageBlock(byId.get(), LanguageCode.Ukr);
        Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByFilmAndLanguageBlock(byId.get(), LanguageCode.Eng);

        films.add(byId.get());
        Set<Mark> marks = markServiceImp.getAllByFilm(films);
        Set<Genre> genres = genreServiceImp.getAllByFilms(films);
        List<Gallery> galleries = galleryServiceImp.getAllByFilm(byId.get());

        FilmUnifier unifier = new FilmUnifier();
        unifier.setFilm(byId.get());
        unifier.setPageTranslationUkr(pageUkr.get());
        unifier.setPageTranslationEng(pageEng.get());
        unifier.setCeoBlockUkr(ceoBlockUkr.get());
        unifier.setCeoBlockEng(ceoBlockEng.get());
        unifier.setMarks(marks);
        unifier.setGenres(genres);
        unifier.setGalleries(galleries);
        FilmDTOAdd dto = FilmMapper.toDTOAdd(unifier);
        return dto;
    }
}
