package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.unifier.PageUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.model.PageDTOAdd;
import com.example.kinocms_admin.model.PageDTOView;
import com.example.kinocms_admin.model.PageMainDTOAdd;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PageMapper {
    public static PageDTOView toDTOView(PageUnifier unifier) {
        PageDTOView dto = new PageDTOView();
        dto.setId(unifier.getPage().getId());
        if (unifier.getPageTranslationUkr() != null) {
            dto.setTitle(unifier.getPageTranslationUkr().getTitle());
        }
        dto.setType(unifier.getPage().getType());
        dto.setStatus(unifier.getPage().isStatus());
        dto.setDateOfCreation(unifier.getPage().getDateOfCreation().toString());
        return dto;
    }

    public static List<PageDTOView> toListPageDTOView(List<PageUnifier> pageUnifiers) {
        return pageUnifiers.stream()
                .map(PageMapper::toDTOView)
                .collect(Collectors.toList());
    }

    public static PageMainDTOAdd toDTOAddMain(PageUnifier unifier) {
        PageMainDTOAdd dto = new PageMainDTOAdd();
        dto.setId(unifier.getPage().getId());
        dto.setPageType(unifier.getPage().getType());
        dto.setStatus(unifier.getPage().isStatus());
        dto.setPhone1(unifier.getPage().getPhoneFirst());
        dto.setPhone2(unifier.getPage().getPhoneSecond());
        dto.setUrlCeo(unifier.getPage().getUrlCeo());

        if (unifier.getCeoBlockUkr() != null) {
            dto.setTitleCeoUkr(unifier.getCeoBlockUkr().getTitle());
            dto.setKeywordsCeoUkr(unifier.getCeoBlockUkr().getKeywords());
            dto.setDescriptionCeoUkr(unifier.getCeoBlockUkr().getDescriptions());
            dto.setSeoTextUkr(unifier.getCeoBlockUkr().getSeoText());
        }
        if (unifier.getCeoBlockEng() != null) {
            dto.setTitleCeoEng(unifier.getCeoBlockEng().getTitle());
            dto.setKeywordsCeoEng(unifier.getCeoBlockEng().getKeywords());
            dto.setDescriptionCeoEng(unifier.getCeoBlockEng().getDescriptions());
            dto.setSeoTextEng(unifier.getCeoBlockEng().getSeoText());
        }
        return dto;
    }

    public static PageUnifier toEntityAddMain(PageMainDTOAdd dto) {
        Page page = new Page();
        page.setId(dto.getId());
        page.setType(PageType.main);
        page.setDateOfCreation(LocalDate.now());
        page.setStatus(dto.getStatus());
        page.setPhoneFirst(dto.getPhone1());
        page.setPhoneSecond(dto.getPhone2());
        page.setUrlCeo(dto.getUrlCeo());

        CeoBlock ceoBlockUkr = new CeoBlock(
                LanguageCode.Ukr,
                PageType.page,
                dto.getTitleCeoUkr(),
                dto.getKeywordsCeoUkr(),
                dto.getDescriptionCeoUkr(),
                dto.getSeoTextUkr(),
                page);
        CeoBlock ceoBlockEng = new CeoBlock(
                LanguageCode.Eng,
                PageType.page,
                dto.getTitleCeoEng(),
                dto.getKeywordsCeoEng(),
                dto.getDescriptionCeoEng(),
                dto.getSeoTextEng(),
                page);

        return new PageUnifier(page, ceoBlockUkr, ceoBlockEng);
    }

    public static PageDTOAdd toDTOAdd(PageUnifier unifier) {
        PageDTOAdd dto = new PageDTOAdd();
        dto.setId(unifier.getPage().getId());
        dto.setPageType(unifier.getPage().getType());
        dto.setUrlCeo(unifier.getPage().getUrlCeo());
        dto.setStatus(unifier.getPage().isStatus());
        dto.setNameBanner(unifier.getPage().getNameBanner());
        dto.setNameImage1(unifier.getPage().getNameImage1());
        dto.setNameImage2(unifier.getPage().getNameImage2());
        dto.setNameImage3(unifier.getPage().getNameImage3());

        if (unifier.getCeoBlockUkr() != null) {
            dto.setTitleCeoUkr(unifier.getCeoBlockUkr().getTitle());
            dto.setDescriptionCeoUkr(unifier.getCeoBlockUkr().getDescriptions());
            dto.setKeywordsCeoUkr(unifier.getCeoBlockUkr().getKeywords());
        }
        if (unifier.getCeoBlockEng() != null) {
            dto.setTitleCeoEng(unifier.getCeoBlockEng().getTitle());
            dto.setDescriptionCeoEng(unifier.getCeoBlockEng().getDescriptions());
            dto.setKeywordsCeoEng(unifier.getCeoBlockEng().getKeywords());
        }
        if (unifier.getPageTranslationUkr() != null) {
            dto.setTitleUkr(unifier.getPageTranslationUkr().getTitle());
            dto.setDescriptionUkr(unifier.getPageTranslationUkr().getDescription());
        }
        if (unifier.getPageTranslationEng() != null) {
            dto.setTitleEng(unifier.getPageTranslationEng().getTitle());
            dto.setDescriptionEng(unifier.getPageTranslationEng().getDescription());
        }
        List<Gallery> galleries = new ArrayList<>();
        if (unifier.getGalleries() != null) {
            for (Gallery g : unifier.getGalleries()) {
                galleries.add(new Gallery(g.getId(), g.getLinkImage(), g.getType()));
            }
        }
        dto.setGalleries(galleries);
        return dto;
    }

    public static PageUnifier toEntityAdd(PageDTOAdd dto){
        Page page = new Page();
        page.setId(dto.getId());
        page.setDateOfCreation(LocalDate.now());
        page.setUrlCeo(dto.getUrlCeo());
        page.setStatus(dto.getStatus());

        CeoBlock ceoBlockUkr = new CeoBlock(
                LanguageCode.Ukr,
                PageType.page,
                dto.getTitleCeoUkr(),
                dto.getKeywordsCeoUkr(),
                dto.getDescriptionCeoUkr(),
                page
        );
        CeoBlock ceoBlockEng = new CeoBlock(
                LanguageCode.Eng,
                PageType.page,
                dto.getTitleCeoEng(),
                dto.getKeywordsCeoEng(),
                dto.getDescriptionCeoEng(),
                page
        );
        PageTranslation translationUkr = new PageTranslation(
                LanguageCode.Ukr,
                PageType.page,
                dto.getTitleUkr(),
                dto.getDescriptionUkr(),
                page
        );
        PageTranslation translationEng = new PageTranslation(
                LanguageCode.Eng,
                PageType.page,
                dto.getTitleEng(),
                dto.getDescriptionEng(),
                page
        );
        return new PageUnifier(page,ceoBlockUkr,ceoBlockEng,translationUkr,translationEng);
    }
}
