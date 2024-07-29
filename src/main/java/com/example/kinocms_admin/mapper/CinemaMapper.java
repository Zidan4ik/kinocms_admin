package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.CinemaUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.model.CinemaDTOAdd;
import com.example.kinocms_admin.model.CinemaDtoView;
import com.example.kinocms_admin.util.HandleDataUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CinemaMapper {
    public static CinemaDTOAdd toDTOAdd(CinemaUnifier unifier) {
        CinemaDTOAdd dto = new CinemaDTOAdd();
        dto.setId(unifier.getCinema().getId());
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
            dto.setConditionUkr(unifier.getPageTranslationUkr().getConditions());
        }
        if (unifier.getPageTranslationEng() != null) {
            dto.setTitleEng(unifier.getPageTranslationEng().getTitle());
            dto.setDescriptionEng(unifier.getPageTranslationEng().getDescription());
            dto.setConditionEng(unifier.getPageTranslationEng().getConditions());
        }

        dto.setNameLogo(unifier.getCinema().getNameLogo());
        dto.setNameBanner(unifier.getCinema().getNameBanner());
        dto.setUrlCeo(unifier.getCinema().getUrlCeo());

        Set<String> marks = new HashSet<>();
        if (unifier.getMarks() != null) {
            for (Mark m : unifier.getMarks()) {
                marks.add(m.getName());
            }
        }
        List<Gallery> galleries = new ArrayList<>();
        if (unifier.getGalleries() != null) {
            for (Gallery g : unifier.getGalleries()) {
                galleries.add(new Gallery(g.getId(), g.getLinkImage(), g.getType()));
            }
        }
        dto.setMarks(marks);
        dto.setHallDTOAddList(unifier.getHalls());
        dto.setGalleries(galleries);
        return dto;
    }

    public static CinemaUnifier toEntityAdd(CinemaDTOAdd dto) {
        Cinema entity = new Cinema();
        entity.setId(dto.getId());
        entity.setUrlCeo(dto.getUrlCeo());
        entity.setDateOfCreation(LocalDate.now());
        Set<Mark> marksResult = new HashSet<>();
        for (String s : dto.getMarks()) {
            marksResult.add(new Mark(HandleDataUtil.substringMark(s)));
        }
        entity.setMarksList(marksResult);

        PageTranslation pageTranslationUkr = new PageTranslation(
                LanguageCode.Ukr,
                PageType.cinema,
                dto.getTitleUkr(),
                dto.getDescriptionUkr(),
                dto.getDescriptionUkr(),
                entity
        );
        PageTranslation pageTranslationEng = new PageTranslation(
                LanguageCode.Eng,
                PageType.cinema,
                dto.getTitleEng(),
                dto.getDescriptionEng(),
                dto.getConditionEng(),
                entity
        );
        CeoBlock ceoBlockUkr = new CeoBlock(
                LanguageCode.Ukr,
                PageType.cinema,
                dto.getTitleCeoUkr(),
                dto.getDescriptionUkr(),
                dto.getKeywordsCeoUkr(),
                entity
        );
        CeoBlock ceoBlockEng = new CeoBlock(
                LanguageCode.Eng,
                PageType.cinema,
                dto.getTitleCeoEng(),
                dto.getDescriptionEng(),
                dto.getKeywordsCeoEng(),
                entity
        );
        List<PageTranslation> pagesTranslators = new ArrayList<>();
        List<CeoBlock> ceoBlocks = new ArrayList<>();

        pagesTranslators.add(pageTranslationUkr);
        pagesTranslators.add(pageTranslationEng);
        ceoBlocks.add(ceoBlockUkr);
        ceoBlocks.add(ceoBlockEng);

        entity.setPageTranslations(pagesTranslators);
        entity.setCeoBlocks(ceoBlocks);
        return new CinemaUnifier(entity,ceoBlockUkr,ceoBlockEng,pageTranslationUkr,pageTranslationEng);
    }
    public static CinemaDtoView toDTOView(Cinema cinema, PageTranslation pageTranslation){
        CinemaDtoView dto = new CinemaDtoView();
        dto.setId(cinema.getId());
        dto.setImageLogo(cinema.getNameLogo());
        dto.setTitle(pageTranslation.getTitle());
        return dto;
    }
}
