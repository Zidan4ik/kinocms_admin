package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.New;
import com.example.kinocms_admin.entity.unifier.CinemaUnifier;
import com.example.kinocms_admin.entity.unifier.FilmUnifier;
import com.example.kinocms_admin.entity.unifier.HallUnifier;
import com.example.kinocms_admin.entity.unifier.NewUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.CinemaMapper;
import com.example.kinocms_admin.mapper.FilmMapper;
import com.example.kinocms_admin.mapper.HallMapper;
import com.example.kinocms_admin.mapper.NewMapper;
import com.example.kinocms_admin.model.*;

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
    private final NewServiceImp newServiceImp;

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
        CinemaUnifier unifier = CinemaMapper.toEntityAdd(cinemaDTO);
        cinemaServiceImp.save(unifier.getCinema(), cinemaDTO.getFileLogo(), cinemaDTO.getFileBanner(), cinemaDTO.getImagesMultipart());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/cinema/{id}/edit")
    public ResponseEntity<Object> editCinema(@ModelAttribute(name = "cinema") CinemaDTOAdd cinemaDTO) {
        CinemaUnifier unifier = CinemaMapper.toEntityAdd(cinemaDTO);
        List<GalleriesDTO> galleriesDTOS = JsonUtil.transformationJsonToObject(cinemaDTO.getGalleryDTO(), GalleriesDTO.class);
        galleryServiceImp.handleDeletingImages(galleriesDTOS, cinemaDTO.getId());

        cinemaServiceImp.save(unifier.getCinema(), cinemaDTO.getFileLogo(), cinemaDTO.getFileBanner(), cinemaDTO.getImagesMultipart());

        Optional<Cinema> cinemaById = cinemaServiceImp.getById(cinemaDTO.getId());
        cinemaById.ifPresent(c -> ceoBlockServiceImp.saveCinema(unifier.getCeoBlockUkr(), c, LanguageCode.Ukr));
        cinemaById.ifPresent(c -> ceoBlockServiceImp.saveCinema(unifier.getCeoBlockEng(), c, LanguageCode.Eng));

        cinemaById.ifPresent(c -> pageTranslationServiceImp.saveCinema(unifier.getPageTranslationUkr(), c, LanguageCode.Ukr));
        cinemaById.ifPresent(c -> pageTranslationServiceImp.saveCinema(unifier.getPageTranslationEng(), c, LanguageCode.Eng));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/cinema/temporary/add")
    public ResponseEntity<Object> addCinemaTemporary(@ModelAttribute(name = "cinema") CinemaDTOAdd cinemaDTO) {
        CinemaUnifier unifier = CinemaMapper.toEntityAdd(cinemaDTO);
        cinemaServiceImp.save(unifier.getCinema(), cinemaDTO.getFileLogo(), cinemaDTO.getFileBanner(), cinemaDTO.getImagesMultipart());
        return ResponseEntity.ok(unifier.getCinema().getId());
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
    public ResponseEntity<Object> editHall(@ModelAttribute(name = "hall") HallDTOAdd hallDTOAdd) {
        HallUnifier unifier = HallMapper.toEntityAdd(hallDTOAdd);

        List<GalleriesDTO> galleries = JsonUtil.transformationJsonToObject(hallDTOAdd.getGalleryDTO(), GalleriesDTO.class);
        galleryServiceImp.handleDeletingImages(galleries, hallDTOAdd.getId());

        Optional<Cinema> cinemaById = cinemaServiceImp.getById(hallDTOAdd.getCinemaId());
        cinemaById.ifPresent(cinema -> unifier.getHall().setCinema(cinema));
        hallServiceImp.save(unifier.getHall(), hallDTOAdd.getFileSchema(), hallDTOAdd.getFileBanner(), hallDTOAdd.getImagesMultipart());
        Optional<Hall> hallById = hallServiceImp.getById(hallDTOAdd.getId());

        hallById.ifPresent(hall -> ceoBlockServiceImp.saveHall(unifier.getCeoBlockUkr(), hall, LanguageCode.Ukr));
        hallById.ifPresent(hall -> ceoBlockServiceImp.saveHall(unifier.getCeoBlockEng(), hall, LanguageCode.Eng));

        hallById.ifPresent(translator -> pageTranslationServiceImp.saveHall(unifier.getPageTranslationUkr(), translator, LanguageCode.Ukr));
        hallById.ifPresent(translator -> pageTranslationServiceImp.saveHall(unifier.getPageTranslationEng(), translator, LanguageCode.Eng));

        return ResponseEntity.ok(hallDTOAdd.getCinemaId());
    }

    @PostMapping("/new/add")
    public ResponseEntity<Object> addNew(@ModelAttribute(name = "newEntity") NewDtoAdd newDtoAdd) {
        NewUnifier unifier = NewMapper.toEntityAdd(newDtoAdd);
        newServiceImp.saveNew(unifier.getNewEntity(), newDtoAdd.getFileImage(), newDtoAdd.getGalleriesMF());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new/{id}/edit")
    public ResponseEntity<Object> editNew(@ModelAttribute(name = "newEntity") NewDtoAdd newDtoAdd) {
        NewUnifier unifier = NewMapper.toEntityAdd(newDtoAdd);
        newServiceImp.saveNew(unifier.getNewEntity(), newDtoAdd.getFileImage(), newDtoAdd.getGalleriesMF());

        Optional<New> newBD = newServiceImp.getById(newDtoAdd.getId());
        ceoBlockServiceImp.saveNew(unifier.getCeoBlockUkr(), newBD.get(), LanguageCode.Ukr);
        ceoBlockServiceImp.saveNew(unifier.getCeoBlockEng(), newBD.get(), LanguageCode.Eng);

        pageTranslationServiceImp.saveNew(unifier.getPageTranslationUkr(), newBD.get(), LanguageCode.Ukr);
        pageTranslationServiceImp.saveNew(unifier.getPageTranslationEng(), newBD.get(), LanguageCode.Eng);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
