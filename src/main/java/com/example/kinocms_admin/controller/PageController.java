package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.unifier.PageUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.mapper.PageMapper;
import com.example.kinocms_admin.service.serviceimp.PageServiceImp;
import com.example.kinocms_admin.service.serviceimp.PageTranslationServiceImp;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class PageController {
    private final PageServiceImp pageServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;

    @GetMapping("/pages")
    public ModelAndView viewPages() {
        ModelAndView model = new ModelAndView("pages/view-pages");
        List<PageUnifier> allAdditional = new ArrayList<>();

        for (Page page : pageServiceImp.getAll()) {
            if (page.getType() != PageType.additional) {
                Optional<PageTranslation> translatorUkr = pageTranslationServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
                model.addObject(page.getType().toString(), PageMapper.toDTOView(new PageUnifier(page,translatorUkr.get())));
            }
        }
        for (Page page : pageServiceImp.getAllByPageType(PageType.additional)) {
            Optional<PageTranslation> translatorUkr = pageTranslationServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
            allAdditional.add(new PageUnifier(page,translatorUkr.get()));
        }
        model.addObject("pagesAdditional", PageMapper.toListPageDTOView(allAdditional));
        return model;
    }
}
