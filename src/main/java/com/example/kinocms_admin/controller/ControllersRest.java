package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.*;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.*;
import com.example.kinocms_admin.model.*;

import com.example.kinocms_admin.service.serviceimp.*;
import com.example.kinocms_admin.util.JsonUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
    private final ShareServiceImp shareServiceImp;
    private final PageServiceImp pageServiceImp;
    private final ContactServiceImp contactServiceImp;
    private final ImageServiceImp imageServiceImp;

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
        newServiceImp.saveNew(unifier.getNewEntity(), newDtoAdd.getFileImage());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new/{id}/edit")
    public ResponseEntity<Object> editNew(@ModelAttribute(name = "newEntity") NewDtoAdd newDtoAdd) {
        NewUnifier unifier = NewMapper.toEntityAdd(newDtoAdd);
        newServiceImp.saveNew(unifier.getNewEntity(), newDtoAdd.getFileImage());

        Optional<New> newBD = newServiceImp.getById(newDtoAdd.getId());
        newBD.ifPresent(c -> ceoBlockServiceImp.saveNew(unifier.getCeoBlockUkr(), c, LanguageCode.Ukr));
        newBD.ifPresent(c -> ceoBlockServiceImp.saveNew(unifier.getCeoBlockEng(), c, LanguageCode.Eng));
        newBD.ifPresent(c -> pageTranslationServiceImp.saveNew(unifier.getPageTranslationUkr(), c, LanguageCode.Ukr));
        newBD.ifPresent(c -> pageTranslationServiceImp.saveNew(unifier.getPageTranslationEng(), c, LanguageCode.Eng));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/share/add")
    public ResponseEntity<Object> addShare(@ModelAttribute(name = "share") ShareDTOAdd shareDTOAdd) {
        ShareUnifier unifier = ShareMapper.toEntityAdd(shareDTOAdd);
        shareServiceImp.saveShare(unifier.getShare(), shareDTOAdd.getFileImage(), shareDTOAdd.getFileBanner());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/share/{id}/edit")
    public ResponseEntity<Object> editShare(@ModelAttribute(name = "share") ShareDTOAdd shareDTOAdd) {
        ShareUnifier unifier = ShareMapper.toEntityAdd(shareDTOAdd);
        shareServiceImp.saveShare(unifier.getShare(), shareDTOAdd.getFileImage(), shareDTOAdd.getFileBanner());

        Optional<Share> shareBD = shareServiceImp.getById(shareDTOAdd.getId());
        shareBD.ifPresent(c -> ceoBlockServiceImp.saveShare(unifier.getCeoBlockUkr(), c, LanguageCode.Ukr));
        shareBD.ifPresent(c -> ceoBlockServiceImp.saveShare(unifier.getCeoBlockEng(), c, LanguageCode.Eng));
        shareBD.ifPresent(c -> pageTranslationServiceImp.saveShare(unifier.getPageTranslationUkr(), c, LanguageCode.Ukr));
        shareBD.ifPresent(c -> pageTranslationServiceImp.saveShare(unifier.getPageTranslationEng(), c, LanguageCode.Eng));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/page-main/{id}/edit")
    public ResponseEntity<Object> editPageMain(@ModelAttribute(name = "page") PageMainDTOAdd dtoAdd) {
        PageUnifier pageUnifier = PageMapper.toEntityAddMain(dtoAdd);
        pageServiceImp.save(pageUnifier.getPage());

        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockUkr(), pageUnifier.getPage(), LanguageCode.Ukr);
        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockEng(), pageUnifier.getPage(), LanguageCode.Eng);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/page/{id}/edit")
    public ResponseEntity<Object> editPage(@ModelAttribute(name = "page") PageDTOAdd dtoAdd) {
        PageUnifier pageUnifier = PageMapper.toEntityAdd(dtoAdd);
        List<GalleriesDTO> galleriesJSON = JsonUtil.transformationJsonToObject(dtoAdd.getGalleriesDTO(), GalleriesDTO.class);
        galleryServiceImp.handleDeletingImages(galleriesJSON, dtoAdd.getId());

        pageServiceImp.saveImages(pageUnifier.getPage(), dtoAdd.getFileBanner(),
                dtoAdd.getFileImage1(), dtoAdd.getFileImage2(), dtoAdd.getFileImage3(), dtoAdd.getFilesGalleries());

        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockUkr(), pageUnifier.getPage(), LanguageCode.Ukr);
        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockEng(), pageUnifier.getPage(), LanguageCode.Eng);
        pageTranslationServiceImp.savePage(pageUnifier.getPageTranslationUkr(), pageUnifier.getPage(), LanguageCode.Ukr);
        pageTranslationServiceImp.savePage(pageUnifier.getPageTranslationEng(), pageUnifier.getPage(), LanguageCode.Eng);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/page/add")
    public ResponseEntity<Object> addPage(@ModelAttribute PageDTOAdd dtoAdd) {
        PageUnifier pageUnifier = PageMapper.toEntityAdd(dtoAdd);

        pageServiceImp.saveImages(pageUnifier.getPage(), dtoAdd.getFileBanner(),
                dtoAdd.getFileImage1(), dtoAdd.getFileImage2(), dtoAdd.getFileImage3(), dtoAdd.getFilesGalleries());

        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockUkr(), pageUnifier.getPage(), LanguageCode.Ukr);
        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockEng(), pageUnifier.getPage(), LanguageCode.Eng);
        pageTranslationServiceImp.savePage(pageUnifier.getPageTranslationUkr(), pageUnifier.getPage(), LanguageCode.Ukr);
        pageTranslationServiceImp.savePage(pageUnifier.getPageTranslationEng(), pageUnifier.getPage(), LanguageCode.Eng);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/page-contact/{id}/edit")
    public ResponseEntity<Object> editPageContact(@ModelAttribute(name = "page") ContactDTOAdd dtoAdd) throws ClassNotFoundException {
        List<ContactDTO> contentTextsParsed = new ArrayList<>();
        if (dtoAdd.getContactsTexts() != null && !dtoAdd.getContactsTexts().isEmpty()) {
            contentTextsParsed = JsonUtil.transformationJsonToObject(dtoAdd.getContactsTexts(), ContactDTO.class);
        }
        List<Contact> newContacts = PageMapper.toListEntityContact(contentTextsParsed);
        PageUnifier pageUnifier = PageMapper.toEntityAddContactPage(dtoAdd, newContacts, dtoAdd.getContactsFiles());

        Optional<Page> pageById = pageServiceImp.getById(dtoAdd.getId());

        if (pageById.isPresent()) {
            List<Contact> currentContacts = contactServiceImp.getAllByPage(pageById.get());
            for (Contact contact : currentContacts) {
                boolean existInNewContacts = newContacts.stream()
                        .filter(newContact -> newContact.getId() != null)
                        .anyMatch(newContact -> newContact.getId().equals(contact.getId()));
                if (!existInNewContacts) {
                    contactServiceImp.delete(contact.getId());
                    imageServiceImp.deleteFiles(GalleriesType.contacts, contact.getId());
                }
            }
        }

        pageServiceImp.save(pageUnifier.getPage());
        if (!newContacts.isEmpty()) {
            for (int i = 0; i < newContacts.size(); i++) {
                contactServiceImp.saveImages(newContacts.get(i), !pageUnifier.getFilesLogo().isEmpty() ? pageUnifier.getFilesLogo().get(i) : null);
            }
        }

        ceoBlockServiceImp.savePage(pageUnifier.getCeoBlockUkr(), pageUnifier.getPage(), LanguageCode.Ukr);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
