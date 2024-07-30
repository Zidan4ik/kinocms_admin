package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.unifier.PageUnifier;
import com.example.kinocms_admin.model.PageDTOView;

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
}
