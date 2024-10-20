package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.model.TemplateDTO;

import java.util.List;

public class TemplateMapper {
    public static TemplateDTO toDTO(Template template){
        TemplateDTO dto = new TemplateDTO();
        dto.setId(template.getId());
        dto.setNameFile(template.getNameFile());
        return dto;
    }
    public static Template toEntity(TemplateDTO dto){
        Template template = new Template();
        template.setId(dto.getId());
        template.setNameFile(dto.getNameFile());
        return template;
    }
    public static List<TemplateDTO> toListDTO(List<Template> templates){
        return templates.stream()
                .map(TemplateMapper::toDTO)
                .toList();
    }
}
