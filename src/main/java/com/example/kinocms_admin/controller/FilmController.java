package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.mapper.GalleryMapper;
import com.example.kinocms_admin.model.FilmDTOAdd;
import com.example.kinocms_admin.model.FilmsDTOView;
import com.example.kinocms_admin.model.GalleryDTO;
import com.example.kinocms_admin.repository.FilmRepository;
import com.example.kinocms_admin.service.serviceimp.FilmServiceImp;
import com.example.kinocms_admin.service.serviceimp.GalleryServiceImp;
import com.example.kinocms_admin.service.serviceimp.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class FilmController {
    private final FilmServiceImp filmServiceImp;
    private final GalleryServiceImp galleryServiceImp;

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
                ModelAndView model = new ModelAndView("films/film-add2");
                return model;
            }

         //    @PostMapping("/film/add")
    //    public ModelAndView addFilmPM(@ModelAttribute(name = "unifierFilm") FilmUnifier unifier){
    //        ModelAndView model = new ModelAndView("redirect:/admin/film/add");
    //        return model;
    //    }

    @GetMapping("/film/data/add")
    @ResponseBody
    public List<GalleryDTO> getFilmData() {

        List<GalleryDTO> galleries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GalleryDTO dto = new GalleryDTO();
            if (dto.getPath() != null) {
                dto.setPath(dto.createPathToImage());
            }
            galleries.add(dto);
        }

        return galleries;
    }
}
