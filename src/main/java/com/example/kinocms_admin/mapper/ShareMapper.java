package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.ShareUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.model.ShareDTOAdd;
import com.example.kinocms_admin.model.ShareDTOView;
import com.example.kinocms_admin.util.HandleDataUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ShareMapper {

    public static ShareDTOAdd toDtoAdd(ShareUnifier unifier) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ShareDTOAdd dto = new ShareDTOAdd();
        dto.setId(unifier.getShare().getId());
        LocalDate dateInput = LocalDate.parse(String.valueOf(unifier.getShare().getDateOfCreation()),inputFormatter);
        dto.setDateOfPublication(dateInput.format(outputFormatter));
        dto.setStatus(unifier.getShare().isStatus());
        dto.setUrlCeo(unifier.getShare().getUrlCeo());
        dto.setNameImage(unifier.getShare().getNameImage());
        dto.setNameBanner(unifier.getShare().getNameBanner());

        Set<String> marks = new HashSet<>();
        if (unifier.getMarks() != null) {
            for (Mark m : unifier.getMarks()) {
                marks.add(m.getName());
            }
        }
        dto.setMarks(marks);

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
            dto.setDescriptionUkr(unifier.getPageTranslationEng().getDescription());
        }
        if (unifier.getPageTranslationEng() != null) {
            dto.setTitleEng(unifier.getPageTranslationEng().getTitle());
            dto.setDescriptionEng(unifier.getPageTranslationEng().getDescription());
        }

        return dto;
    }

    public static ShareUnifier toEntityAdd(ShareDTOAdd dto) {
        Share entity = new Share();
        entity.setId(dto.getId());
        entity.setNameImage(dto.getNameImage());
        entity.setDateOfCreation(LocalDate.parse(dto.getDateOfPublication(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        entity.setStatus(dto.getStatus());
        entity.setUrlCeo(dto.getUrlCeo());

        Set<Mark> marksResult = new HashSet<>();
        for (String s : dto.getMarks()) {
            marksResult.add(new Mark(HandleDataUtil.substringMark(s)));
        }
        entity.setMarksList(marksResult);

        PageTranslation pageTranslationUkr = new PageTranslation(
                LanguageCode.Ukr,
                PageType.news,
                dto.getTitleUkr(),
                dto.getDescriptionUkr(),
                entity
        );
        PageTranslation pageTranslationEng = new PageTranslation(
                LanguageCode.Eng,
                PageType.news,
                dto.getTitleEng(),
                dto.getDescriptionEng(),
                entity
        );
        CeoBlock ceoBlockUkr = new CeoBlock(
                LanguageCode.Ukr,
                PageType.news,
                dto.getTitleCeoUkr(),
                dto.getDescriptionCeoUkr(),
                dto.getKeywordsCeoUkr(),
                entity
        );
        CeoBlock ceoBlockEng = new CeoBlock(
                LanguageCode.Eng,
                PageType.news,
                dto.getTitleCeoEng(),
                dto.getDescriptionCeoEng(),
                dto.getKeywordsCeoEng(),
                entity
        );
        List<CeoBlock> ceoBlocks = new ArrayList<>(){{
            add(ceoBlockUkr);
            add(ceoBlockEng);
        }};
        List<PageTranslation> pageTranslations = new ArrayList<>(){{
            add(pageTranslationUkr);
            add(pageTranslationEng);
        }};
        entity.setCeoBlocks(ceoBlocks);
        entity.setPageTranslations(pageTranslations);
        return new ShareUnifier(entity,ceoBlockUkr,ceoBlockEng,pageTranslationUkr,pageTranslationEng);
    }
    public static ShareDTOView toDTOView(ShareUnifier unifier) {
        ShareDTOView dto = new ShareDTOView();
        dto.setId(unifier.getShare().getId());
        dto.setName(unifier.getPageTranslationUkr().getTitle());
        if (unifier.getShare().getDateOfCreation() != null) {
            dto.setDateOfCreation(unifier.getShare().getDateOfCreation().toString());
        }
        dto.setStatus(unifier.getShare().isStatus());
        return dto;
    }
    public static List<ShareDTOView> toListDtoView(List<ShareUnifier> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ShareMapper::toDTOView)
                .collect(Collectors.toList());
    }
}
