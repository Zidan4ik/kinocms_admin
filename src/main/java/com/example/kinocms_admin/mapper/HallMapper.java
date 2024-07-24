package com.example.kinocms_admin.mapper;


import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.unifier.HallUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.model.HallDTOAdd;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HallMapper {
    public static HallUnifier toEntityAdd(HallDTOAdd dto) {
        HallUnifier unifier = new HallUnifier();
        Hall hall = new Hall();
        hall.setId(dto.getId());
        hall.setNumber(Integer.valueOf(dto.getNumber()));
        hall.setUrlCeo(dto.getUrlCeo());
        hall.setDateOfCreation(LocalDate.now());
        unifier.setHall(hall);
        PageTranslation translatorUkr = new PageTranslation(
                LanguageCode.Ukr,
                PageType.hall,
                dto.getDescriptionUkr(),
                hall
        );
        PageTranslation translatorEng = new PageTranslation(
                LanguageCode.Eng,
                PageType.hall,
                dto.getDescriptionEng(),
                hall
        );
        CeoBlock ceoBlockUkr = new CeoBlock(
                LanguageCode.Ukr,
                PageType.hall,
                dto.getTitleCeoUkr(),
                dto.getDescriptionCeoUkr(),
                dto.getKeywordsCeoUkr(),
                hall
        );
        CeoBlock ceoBlockEng = new CeoBlock(
                LanguageCode.Eng,
                PageType.hall,
                dto.getTitleCeoEng(),
                dto.getDescriptionCeoEng(),
                dto.getKeywordsCeoEng(),
                hall
        );

        return new HallUnifier(hall,translatorUkr,translatorEng,ceoBlockUkr,ceoBlockEng);
    }
    public static HallDTOAdd toDTOAdd(HallUnifier unifier){
        HallDTOAdd dto = new HallDTOAdd();
        dto.setId(unifier.getHall().getId());
        dto.setNumber(String.valueOf(unifier.getHall().getNumber()));
        dto.setNameBanner(unifier.getHall().getNameBanner());
        dto.setNameSchema(unifier.getHall().getNameSchema());
        dto.setUrlCeo(unifier.getHall().getUrlCeo());

        dto.setDescriptionUkr(unifier.getPageTranslationUkr().getDescription());
        dto.setDescriptionEng(unifier.getPageTranslationEng().getDescription());

        dto.setTitleCeoUkr(unifier.getCeoBlockUkr().getTitle());
        dto.setDescriptionCeoUkr(unifier.getCeoBlockUkr().getDescriptions());
        dto.setKeywordsCeoUkr(unifier.getCeoBlockUkr().getKeywords());

        dto.setTitleCeoEng(unifier.getCeoBlockEng().getTitle());
        dto.setDescriptionCeoEng(unifier.getCeoBlockEng().getDescriptions());
        dto.setKeywordsCeoEng(unifier.getCeoBlockEng().getKeywords());

        List<Gallery> galleries = new ArrayList<>();
        if (unifier.getGalleries() != null) {
            for (Gallery g : unifier.getGalleries()) {
                galleries.add(new Gallery(g.getId(), g.getLinkImage(), g.getType()));
            }
        }
        dto.setGalleries(galleries);
        return dto;
    }
}
