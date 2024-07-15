package com.example.kinocms_admin.controller;


import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.model.FilmDTOAdd;

import com.example.kinocms_admin.model.GalleriesDTO;
import com.example.kinocms_admin.service.serviceimp.*;
import com.example.kinocms_admin.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final GalleryServiceImp galleryServiceImp;

    @PostMapping(value = "/film/add")
    public ResponseEntity<Object> addFilm(
            @ModelAttribute(name = "film") FilmDTOAdd filmDTO) {
        FilmUnifier entityAdd = FilmMapper.toEntityAdd(filmDTO);

        filmServiceImp.save(entityAdd.getFilm(), filmDTO.getFileImage(), filmDTO.getImagesMultipart());
        ceoBlockServiceImp.save(entityAdd.getCeoBlockEng(), entityAdd.getFilm(), LanguageCode.Eng);
        ceoBlockServiceImp.save(entityAdd.getCeoBlockUkr(), entityAdd.getFilm(), LanguageCode.Ukr);
        pageTranslationServiceImp.save(entityAdd.getPageTranslationEng(), entityAdd.getFilm(), LanguageCode.Eng);
        pageTranslationServiceImp.save(entityAdd.getPageTranslationUkr(), entityAdd.getFilm(), LanguageCode.Ukr);

//        ServiceResponse<FilmDTOAdd> response = new ServiceResponse<>("success", filmDTO);
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/film/{id}/edit")
    public ResponseEntity<Object> editFilm(@ModelAttribute(name = "film") FilmDTOAdd filmDTO) {
        FilmUnifier entityAdd = FilmMapper.toEntityAdd(filmDTO);
        List<GalleriesDTO> galleries = JsonUtil.transformationJsonToObject(filmDTO.getGalleryDTO(), GalleriesDTO.class);
        galleryServiceImp.handleDeletingImages(galleries, filmDTO.getId());

        filmServiceImp.save(entityAdd.getFilm(), filmDTO.getFileImage(), filmDTO.getImagesMultipart());
        ceoBlockServiceImp.save(entityAdd.getCeoBlockEng(), entityAdd.getFilm(), LanguageCode.Eng);
        ceoBlockServiceImp.save(entityAdd.getCeoBlockUkr(), entityAdd.getFilm(), LanguageCode.Ukr);

        pageTranslationServiceImp.save(entityAdd.getPageTranslationEng(), entityAdd.getFilm(), LanguageCode.Eng);
        pageTranslationServiceImp.save(entityAdd.getPageTranslationUkr(), entityAdd.getFilm(), LanguageCode.Ukr);

//        ServiceResponse<FilmDTOAdd> response = new ServiceResponse<>("success", filmDTO);
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
