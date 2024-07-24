package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.unifier.HallUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.HallMapper;
import com.example.kinocms_admin.model.HallDTOAdd;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class HallController {
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final GalleryServiceImp galleryServiceImp;
    private final HallServiceImp hallServiceImp;
    @GetMapping("/hall/add")
    public ModelAndView addHall(@ModelAttribute(name = "hall") HallDTOAdd dtoAdd) {
        ModelAndView model = new ModelAndView("hall/hall-add");
        if (dtoAdd.getCinemaId() != null) {
            model.addObject("cinemaId", dtoAdd.getCinemaId());
        }
        return model;
    }

    @GetMapping("/hall/{id}/edit")
    public ModelAndView editHall(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("hall/hall-edit");
        Optional<Hall> hallById = hallServiceImp.getById(id);
        model.addObject("hall", hallById.get());
        model.addObject("cinemaId", hallById.get().getCinema().getId());
        return model;
    }

    @GetMapping("/hall/{id}")
    @ResponseBody
    public HallDTOAdd getHallDate(@PathVariable(name = "id") Long id) {
        Optional<Hall> hallById = hallServiceImp.getById(id);
        Optional<PageTranslation> pageTranslationUkr = pageTranslationServiceImp.getByHallAndLanguageCode(hallById.get(), LanguageCode.Ukr);
        Optional<PageTranslation> pageTranslationEng = pageTranslationServiceImp.getByHallAndLanguageCode(hallById.get(), LanguageCode.Eng);
        Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByHallAndLanguageCode(hallById.get(),LanguageCode.Ukr);
        Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByHallAndLanguageCode(hallById.get(),LanguageCode.Eng);
        List<Gallery> galleries = galleryServiceImp.getAllByHall(hallById.get());

        HallUnifier unifier = new HallUnifier(
                hallById.get(),
                pageTranslationUkr.get(),
                pageTranslationEng.get(),
                ceoBlockUkr.get(),
                ceoBlockEng.get(),
                galleries
        );
        HallDTOAdd dtoAdd = HallMapper.toDTOAdd(unifier);
        return dtoAdd;
    }
    @GetMapping("/hall/{id}/delete")
    public ModelAndView deleteHall(@PathVariable Long id,
                                   @RequestParam(name = "cinemaId") Long cinemaId){
        ModelAndView model = new ModelAndView("redirect:/admin/cinema/add?cinemaId="+cinemaId);
        hallServiceImp.deleteById(id);
        return model;
    }
}
