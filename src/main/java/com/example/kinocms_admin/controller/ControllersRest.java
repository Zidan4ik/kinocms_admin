package com.example.kinocms_admin.controller;


import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.entity.unifier.HallUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.CinemaMapper;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.mapper.HallMapper;
import com.example.kinocms_admin.model.CinemaDTOAdd;
import com.example.kinocms_admin.model.FilmDTOAdd;

import com.example.kinocms_admin.model.GalleriesDTO;
import com.example.kinocms_admin.model.HallDTOAdd;
import com.example.kinocms_admin.service.serviceimp.*;
import com.example.kinocms_admin.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ControllersRest {
    private final FilmServiceImp filmServiceImp;
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final GalleryServiceImp galleryServiceImp;
    private final CinemaServiceImp cinemaServiceImp;
    private final HallServiceImp hallServiceImp;

    @PostMapping(value = "/film/add")
    public ResponseEntity<Object> addFilm(
            @ModelAttribute(name = "film") FilmDTOAdd filmDTO) {
        FilmUnifier entityAdd = FilmMapper.toEntityAdd(filmDTO);

        filmServiceImp.save(entityAdd.getFilm(), filmDTO.getFileImage(), filmDTO.getImagesMultipart());
        ceoBlockServiceImp.saveFilm(entityAdd.getCeoBlockEng(), entityAdd.getFilm(), LanguageCode.Eng);
        ceoBlockServiceImp.saveFilm(entityAdd.getCeoBlockUkr(), entityAdd.getFilm(), LanguageCode.Ukr);
        pageTranslationServiceImp.saveFilm(entityAdd.getPageTranslationEng(), entityAdd.getFilm(), LanguageCode.Eng);
        pageTranslationServiceImp.saveFilm(entityAdd.getPageTranslationUkr(), entityAdd.getFilm(), LanguageCode.Ukr);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/film/{id}/edit")
    public ResponseEntity<Object> editFilm(@ModelAttribute(name = "film") FilmDTOAdd filmDTO) {
        FilmUnifier entityAdd = FilmMapper.toEntityAdd(filmDTO);
        List<GalleriesDTO> galleries = JsonUtil.transformationJsonToObject(filmDTO.getGalleryDTO(), GalleriesDTO.class);
        galleryServiceImp.handleDeletingImages(galleries, filmDTO.getId());

        filmServiceImp.save(entityAdd.getFilm(), filmDTO.getFileImage(), filmDTO.getImagesMultipart());
        ceoBlockServiceImp.saveFilm(entityAdd.getCeoBlockEng(), entityAdd.getFilm(), LanguageCode.Eng);
        ceoBlockServiceImp.saveFilm(entityAdd.getCeoBlockUkr(), entityAdd.getFilm(), LanguageCode.Ukr);

        pageTranslationServiceImp.saveFilm(entityAdd.getPageTranslationEng(), entityAdd.getFilm(), LanguageCode.Eng);
        pageTranslationServiceImp.saveFilm(entityAdd.getPageTranslationUkr(), entityAdd.getFilm(), LanguageCode.Ukr);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/cinema/add")
    public ResponseEntity<Object> addCinema(@ModelAttribute(name = "cinema") CinemaDTOAdd cinemaDTO) {
        Cinema cinemaEntity = CinemaMapper.toEntityAdd(cinemaDTO);
        cinemaServiceImp.save(cinemaEntity, cinemaDTO.getFileLogo(), cinemaDTO.getFileBanner(), cinemaDTO.getImagesMultipart());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping(value = "/cinema/{id}/edit")
    public ResponseEntity<Object> editCinema(@ModelAttribute(name = "cinema") CinemaDTOAdd cinemaDTO) {
        Cinema cinemaEntity = CinemaMapper.toEntityAdd(cinemaDTO);
        cinemaServiceImp.save(cinemaEntity, cinemaDTO.getFileLogo(), cinemaDTO.getFileBanner(), cinemaDTO.getImagesMultipart());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/cinema/temporary/add")
    public ResponseEntity<Object> addCinemaTemporary(@ModelAttribute(name = "cinema") CinemaDTOAdd cinemaDTO) {
        Cinema cinemaEntity = CinemaMapper.toEntityAdd(cinemaDTO);
        cinemaServiceImp.save(cinemaEntity, cinemaDTO.getFileLogo(), cinemaDTO.getFileBanner(), cinemaDTO.getImagesMultipart());
        return ResponseEntity.ok(cinemaEntity.getId());
    }

    @PostMapping("/hall/add")
    public ResponseEntity<Object> addHall(@ModelAttribute(name = "hall") HallDTOAdd hallDTO) {
        if (hallDTO.getCinemaId() == null) {
            Cinema cinema = new Cinema();
            cinemaServiceImp.save(cinema, null, null, null);
            hallDTO.setCinemaId(cinema.getId());
        }
        Cinema cinemaEntity = cinemaServiceImp.getById(hallDTO.getCinemaId()).get();
        HallUnifier unifierHall = HallMapper.toEntityAdd(hallDTO);
        unifierHall.getHall().setCinema(cinemaEntity);
        hallServiceImp.save(unifierHall.getHall(), hallDTO.getFileSchema(), hallDTO.getFileBanner(), hallDTO.getImagesMultipart());
        return ResponseEntity.ok(hallDTO.getCinemaId());
    }
    @PostMapping("/hall/{id}/edit")
    public ResponseEntity<Object> editHall(@ModelAttribute(name = "hall") HallDTOAdd hallDTOAdd){
        HallUnifier unifier = HallMapper.toEntityAdd(hallDTOAdd);
        Optional<Cinema> cinemaById = cinemaServiceImp.getById(hallDTOAdd.getCinemaId());
        unifier.getHall().setCinema(cinemaById.get());
        hallServiceImp.save(unifier.getHall(),hallDTOAdd.getFileSchema(),hallDTOAdd.getFileBanner(),hallDTOAdd.getImagesMultipart());

        Optional<Hall> hallById = hallServiceImp.getById(hallDTOAdd.getId());
        ceoBlockServiceImp.saveHall(unifier.getCeoBlockUkr(),hallById.get(),LanguageCode.Ukr);
        ceoBlockServiceImp.saveHall(unifier.getCeoBlockEng(),hallById.get(),LanguageCode.Eng);

        pageTranslationServiceImp.saveHall(unifier.getPageTranslationUkr(),hallById.get(),LanguageCode.Ukr);
        pageTranslationServiceImp.saveHall(unifier.getPageTranslationEng(),hallById.get(),LanguageCode.Eng);

        return ResponseEntity.ok(hallDTOAdd.getCinemaId());
    }
}
