package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.Mark;
import com.example.kinocms_admin.entity.unifier.CinemaUnifier;
import com.example.kinocms_admin.model.CinemaDTOAdd;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class CinemaMapper {
    public static CinemaDTOAdd toDTOAdd(CinemaUnifier unifier) {
        CinemaDTOAdd dto = new CinemaDTOAdd();
        dto.setId(unifier.getCinema().getId());

        dto.setTitleCeoUkr(unifier.getCeoBlockUkr().getTitle());
        dto.setDescriptionCeoUkr(unifier.getCeoBlockUkr().getDescriptions());
        dto.setKeywordsCeoUkr(unifier.getCeoBlockUkr().getKeywords());

        dto.setTitleCeoEng(unifier.getCeoBlockEng().getTitle());
        dto.setDescriptionCeoEng(unifier.getCeoBlockEng().getDescriptions());
        dto.setKeywordsCeoEng(unifier.getCeoBlockEng().getKeywords());

        dto.setTitleUkr(unifier.getPageTranslationUkr().getTitle());
        dto.setDescriptionUkr(unifier.getPageTranslationUkr().getDescription());
        dto.setConditionUkr(unifier.getPageTranslationUkr().getConditions());

        dto.setTitleEng(unifier.getPageTranslationEng().getTitle());
        dto.setDescriptionEng(unifier.getPageTranslationEng().getDescription());
        dto.setConditionEng(unifier.getPageTranslationEng().getConditions());

        dto.setNameLogo(unifier.getCinema().getNameLogo());
        dto.setNameBanner(unifier.getCinema().getNameBanner());
        dto.setUrlCeo(unifier.getCinema().getUrlCeo());

        Set<String> marks = new HashSet<>();
        for (Mark m : unifier.getMarks()) {
            marks.add(m.getName());
        }
        dto.setMarks(marks);
        dto.setHallDTOAddList(unifier.getHalls());
        dto.setGalleries(unifier.getGalleries());

        return dto;
    }
}
