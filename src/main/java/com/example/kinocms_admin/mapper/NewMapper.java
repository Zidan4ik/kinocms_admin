package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.entity.unifier.NewUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import com.example.kinocms_admin.model.NewDtoAdd;
import com.example.kinocms_admin.model.NewsDTOView;
import com.example.kinocms_admin.util.HandleDataUtil;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class NewMapper {
    public static NewsDTOView toDTOView(NewUnifier unifier) {
        NewsDTOView dto = new NewsDTOView();
        dto.setId(unifier.getNewEntity().getId());
        dto.setName(unifier.getPageTranslationUkr().getTitle());
        if (unifier.getNewEntity().getDateOfCreation() != null) {
            dto.setDateOfCreation(unifier.getNewEntity().getDateOfCreation().toString());
        }
        dto.setStatus(unifier.getNewEntity().isStatus());
        return dto;
    }

    public static NewDtoAdd toDtoAdd(NewUnifier unifier) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        NewDtoAdd dto = new NewDtoAdd();
        dto.setId(unifier.getNewEntity().getId());
        LocalDate dateInput = LocalDate.parse(String.valueOf(unifier.getNewEntity().getDateOfCreation()),inputFormatter);
        dto.setDateOfPublication(dateInput.format(outputFormatter));
        dto.setStatus(unifier.getNewEntity().isStatus());
        dto.setUrlCeo(unifier.getNewEntity().getUrlCeo());
        dto.setNameImage(unifier.getNewEntity().getNameImage());

        Set<String> marks = new HashSet<>();
        if (unifier.getMarks() != null) {
            for (Mark m : unifier.getMarks()) {
                marks.add(m.getName());
            }
        }
        dto.setMarks(marks);

        List<Gallery> galleries = new ArrayList<>();
        if (unifier.getGalleryList() != null) {
            for (Gallery g : unifier.getGalleryList()) {
                galleries.add(new Gallery(g.getId(), g.getLinkImage(), g.getType()));
            }
        }
        dto.setGalleriesBD(galleries);

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

    public static NewUnifier toEntityAdd(NewDtoAdd dto) {
        New entity = new New();
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
        return new NewUnifier(entity,ceoBlockUkr,ceoBlockEng,pageTranslationUkr,pageTranslationEng);
    }

    public static List<NewsDTOView> toListDtoView(List<NewUnifier> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(NewMapper::toDTOView)
                .collect(Collectors.toList());
    }
}
