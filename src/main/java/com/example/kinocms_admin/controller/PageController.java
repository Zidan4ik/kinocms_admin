package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.PageUnifier;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.mapper.PageMapper;
import com.example.kinocms_admin.model.ContactDTOAdd;
import com.example.kinocms_admin.model.PageDTOAdd;
import com.example.kinocms_admin.model.PageMainDTOAdd;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final GalleryServiceImp galleryServiceImp;
    private final ImageServiceImp imageServiceImp;
    private final ContactServiceImp contactServiceImp;

    @GetMapping("/pages")
    public ModelAndView viewPages() {
        ModelAndView model = new ModelAndView("pages/view-pages");
        List<PageUnifier> allAdditional = new ArrayList<>();

        for (Page page : pageServiceImp.getAll()) {
            if (page.getType() != PageType.additional) {
                Optional<PageTranslation> translatorUkr = pageTranslationServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
                translatorUkr.ifPresent(p ->
                {
                    model.addObject(page.getType().toString(), PageMapper.toDTOView(new PageUnifier(page, p)));
                });
            }
        }
        for (Page page : pageServiceImp.getAllByPageType(PageType.additional)) {
            Optional<PageTranslation> translatorUkr = pageTranslationServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
            translatorUkr.ifPresent(p -> allAdditional.add(new PageUnifier(page, p)));
        }
        model.addObject("pagesAdditional", PageMapper.toListPageDTOView(allAdditional));
        return model;
    }

    @GetMapping("/page/add")
    public String addPage() {
        return "pages/page-add";
    }

    @GetMapping("/page/{id}/edit")
    public ModelAndView editPage(@PathVariable Long id) {
        ModelAndView model = new ModelAndView();
        Optional<Page> pageById = pageServiceImp.getById(id);
        if (pageById.isPresent()) {
            Page page = pageById.get();
            if (page.getType() == PageType.main) {
                model.setViewName("pages/main-edit");
            } else if (page.getType() == PageType.contact) {
                model.setViewName("pages/contact-edit");
            } else {
                model.setViewName("pages/page-edit");
                Optional<PageTranslation> translationUkr = pageTranslationServiceImp.getByPageAndLanguageCode(pageById.get(), LanguageCode.Ukr);
                translationUkr.ifPresent(t -> {
                    model.addObject("titleUkr", t.getTitle());
                });
            }
        }
        model.addObject("pageId", id);
        return model;
    }

    @GetMapping("/page-main/{id}")
    @ResponseBody
    public PageMainDTOAdd getPageMain(@PathVariable Long id) {
        Optional<Page> pageById = pageServiceImp.getById(id);
        PageUnifier unifier = new PageUnifier();
        if (pageById.isPresent()) {
            Page page = pageById.get();
            Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
            Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByPageAndLanguageCode(page, LanguageCode.Eng);

            unifier.setPage(page);
            ceoBlockUkr.ifPresent(unifier::setCeoBlockUkr);
            ceoBlockEng.ifPresent(unifier::setCeoBlockEng);
        }
        return PageMapper.toDTOAddMain(unifier);
    }

    @GetMapping("/page/{id}")
    @ResponseBody
    public PageDTOAdd getPage(@PathVariable Long id) {
        Optional<Page> pageById = pageServiceImp.getById(id);
        PageUnifier unifier = new PageUnifier();
        if (pageById.isPresent()) {
            Page page = pageById.get();
            Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
            Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByPageAndLanguageCode(page, LanguageCode.Eng);
            Optional<PageTranslation> translationUkr = pageTranslationServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);
            Optional<PageTranslation> translationEng = pageTranslationServiceImp.getByPageAndLanguageCode(page, LanguageCode.Eng);
            List<Gallery> galleriesByPage = galleryServiceImp.getAllByPage(page);

            unifier.setPage(page);
            unifier.setGalleries(galleriesByPage);
            ceoBlockUkr.ifPresent(unifier::setCeoBlockUkr);
            ceoBlockEng.ifPresent(unifier::setCeoBlockEng);
            translationUkr.ifPresent(unifier::setPageTranslationUkr);
            translationEng.ifPresent(unifier::setPageTranslationEng);
        }
        return PageMapper.toDTOAdd(unifier);
    }

    @GetMapping("/page/{id}/delete")
    public ModelAndView deletePage(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("redirect:/admin/pages");
        pageServiceImp.deleteById(id);
        imageServiceImp.deleteFiles(GalleriesType.pages, id);
        return model;
    }

    @GetMapping("/contact/{id}")
    @ResponseBody
    public ContactDTOAdd getContact(@PathVariable Long id) {
        Optional<Page> pageById = pageServiceImp.getById(id);
        PageUnifier unifier = new PageUnifier();
        if (pageById.isPresent()) {
            Page page = pageById.get();
            List<Contact> contacts = contactServiceImp.getAllByPage(page);
            Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByPageAndLanguageCode(page, LanguageCode.Ukr);

            unifier.setPage(page);
            unifier.setContacts(contacts);
            ceoBlockUkr.ifPresent(unifier::setCeoBlockUkr);
        }

        return PageMapper.toDTOAddContact(unifier);
    }
}
