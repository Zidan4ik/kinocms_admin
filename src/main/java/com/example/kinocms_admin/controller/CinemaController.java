package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.CinemaUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.CinemaMapper;
import com.example.kinocms_admin.model.CinemaDTOAdd;
import com.example.kinocms_admin.model.CinemaDtoView;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaServiceImp cinemaServiceImp;
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final GalleryServiceImp galleryServiceImp;
    private final HallServiceImp hallServiceImp;
    private final MarkServiceImp markServiceImp;

    @GetMapping("/cinemas")
    public ModelAndView viewCinemas() {
        ModelAndView model = new ModelAndView("cinemas/view-cinemas");
        List<CinemaDtoView> dtoViews = new ArrayList<>();
        for (Cinema cinema : cinemaServiceImp.getAll()) {
            Optional<PageTranslation> translator = pageTranslationServiceImp.getByCinemaAndLanguageCode(cinema, LanguageCode.Ukr);
            translator.ifPresent(pageTranslation -> dtoViews.add(CinemaMapper.toDTOView(cinema, pageTranslation)));
        }
        model.addObject("cinemasDTO", dtoViews);
        return model;
    }

    @GetMapping("/cinema/add")
    public ModelAndView addCinema(@RequestParam(name = "cinemaId", required = false) Long id) {
        ModelAndView model = new ModelAndView("cinemas/cinema-add");
        CinemaDTOAdd cinemaDtoAdd = new CinemaDTOAdd();
        if (id != null) {
            Optional<Cinema> cinemaBD = cinemaServiceImp.getById(id);
            if (cinemaBD.isPresent()) {
                CinemaUnifier unifier = new CinemaUnifier();
                unifier.setCinema(cinemaBD.get());
                unifier.setHalls(hallServiceImp.getAllByCinema(cinemaBD.get()));
                cinemaDtoAdd = CinemaMapper.toDTOAdd(unifier);
            }
        }

        model.addObject("cinema", cinemaDtoAdd);
        return model;
    }

    @GetMapping("/cinema/{id}")
    @ResponseBody
    public CinemaDTOAdd getCinemaData(@PathVariable(name = "id") Long id) {
        CinemaUnifier unifier = new CinemaUnifier();
        Optional<Cinema> cinemaById = cinemaServiceImp.getById(id);
        if (cinemaById.isPresent()) {
            Optional<PageTranslation> pageTranslationUkr = pageTranslationServiceImp.getByCinemaAndLanguageCode(cinemaById.get(), LanguageCode.Ukr);
            Optional<PageTranslation> pageTranslationEng = pageTranslationServiceImp.getByCinemaAndLanguageCode(cinemaById.get(), LanguageCode.Eng);
            Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByCinemaAndLanguageCode(cinemaById.get(), LanguageCode.Ukr);
            Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByCinemaAndLanguageCode(cinemaById.get(), LanguageCode.Eng);
            Set<Mark> marksByCinema = markServiceImp.getAllByCinema(cinemaById.get());

            List<Gallery> galleryByCinema = galleryServiceImp.getAllByCinema(cinemaById.get());
            unifier.setCinema(cinemaById.get());
            pageTranslationUkr.ifPresent(unifier::setPageTranslationUkr);
            pageTranslationEng.ifPresent(unifier::setPageTranslationEng);
            ceoBlockUkr.ifPresent(unifier::setCeoBlockUkr);
            ceoBlockEng.ifPresent(unifier::setCeoBlockEng);
            unifier.setMarks(marksByCinema);
            unifier.setGalleries(galleryByCinema);
        }
        return CinemaMapper.toDTOAdd(unifier);
    }

    @GetMapping("/cinema/{id}/edit")
    public ModelAndView editCinema(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("cinemas/cinema-edit");
        Optional<Cinema> cinemaById = cinemaServiceImp.getById(id);

        CinemaUnifier unifier = new CinemaUnifier();
        unifier.setCinema(cinemaById.get());
        unifier.setHalls(hallServiceImp.getAllByCinema(cinemaById.get()));
        CinemaDTOAdd dtoAdd = CinemaMapper.toDTOAdd(unifier);

        model.addObject("cinema", dtoAdd);
        return model;
    }
    @GetMapping("/cinema/{id}/delete")
    public ModelAndView deleteCinema(@PathVariable Long id){
        ModelAndView model = new ModelAndView("redirect:/admin/cinemas");
        cinemaServiceImp.deleteById(id);
        return model;
    }
}

