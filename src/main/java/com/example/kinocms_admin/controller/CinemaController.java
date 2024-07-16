package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.Cinema;
import com.example.kinocms_admin.entity.unifier.CinemaUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.CinemaMapper;
import com.example.kinocms_admin.model.CinemaDTOAdd;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

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
    public ModelAndView viewCinemas(){
        ModelAndView model = new ModelAndView("cinemas/view-cinemas");
        List<Cinema> cinemas = cinemaServiceImp.getAll();

        model.addObject("cinemasDTO",cinemas);
        return model;
    }
    @GetMapping("/cinema/add")
        public ModelAndView addCinema(@ModelAttribute(name = "cinema")CinemaDTOAdd dtoAdd,
                                  @RequestParam(name = "cinemaId") Long id){
        ModelAndView model = new ModelAndView("cinemas/cinema-add");
        CinemaDTOAdd cinemaDtoAdd = new CinemaDTOAdd();
        if(id != null){
            Optional<Cinema> cinemaBD = cinemaServiceImp.getById(id);
            if(cinemaBD.isPresent()){
                CinemaUnifier unifier = new CinemaUnifier();
                unifier.setCinema(cinemaBD.get());
                unifier.setCeoBlockUkr(ceoBlockServiceImp.getByCinemaAndLanguageCode(cinemaBD.get(), LanguageCode.Ukr).get());
                unifier.setCeoBlockEng(ceoBlockServiceImp.getByCinemaAndLanguageCode(cinemaBD.get(), LanguageCode.Eng).get());
                unifier.setPageTranslationUkr(pageTranslationServiceImp.getByCinemaAndLanguageCode(cinemaBD.get(),LanguageCode.Ukr).get());
                unifier.setPageTranslationEng(pageTranslationServiceImp.getByCinemaAndLanguageCode(cinemaBD.get(),LanguageCode.Eng).get());
                unifier.setGalleries(galleryServiceImp.getAllByCinema(cinemaBD.get()));
                unifier.setMarks(markServiceImp.getAllByCinema(cinemaBD.get()));
                unifier.setHalls(hallServiceImp.getAllByCinema(cinemaBD.get()));
            }
        }
        model.addObject("cinema",cinemaDtoAdd);
        return model;
    }
}
