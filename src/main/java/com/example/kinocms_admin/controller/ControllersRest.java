package com.example.kinocms_admin.controller;


import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.model.FilmDTOAdd;

import com.example.kinocms_admin.service.serviceimp.FilmServiceImp;
import com.example.kinocms_admin.service.serviceimp.GalleryServiceImp;
import com.example.kinocms_admin.service.serviceimp.GenreServiceImp;
import com.example.kinocms_admin.service.serviceimp.MarkServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ControllersRest {
    private final FilmServiceImp filmServiceImp;

    @PostMapping(value = "/film/add")
    public ResponseEntity<Object> addFilmPM(
            @ModelAttribute(name = "film") FilmDTOAdd filmDTO) {
        FilmUnifier entityAdd = FilmMapper.toEntityAdd(filmDTO);

        entityAdd.getFilm().setGenresList(entityAdd.getGenres());
        entityAdd.getFilm().setMarksList(entityAdd.getMarks());
        entityAdd.getFilm().setCeoBlock(entityAdd.getCeoBlock());

        filmServiceImp.save(entityAdd.getFilm(),filmDTO.getFileImage(), filmDTO.getImagesMultipart());


//        ServiceResponse<FilmDTOAdd> response = new ServiceResponse<>("success", filmDTO);
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

