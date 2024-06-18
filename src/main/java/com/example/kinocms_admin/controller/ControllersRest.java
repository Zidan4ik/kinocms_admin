package com.example.kinocms_admin.controller;


import com.example.kinocms_admin.entity.Film;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.model.FilmDTOAdd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ControllersRest {
    @PostMapping(value = "/film/add")
    public ResponseEntity<Object> addFilmPM(
            @ModelAttribute(name = "film") FilmDTOAdd filmDTO) {

        FilmMapper.toEntityAdd(filmDTO);
//        ServiceResponse<FilmDTOAdd> response = new ServiceResponse<>("success", filmDTO);
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

