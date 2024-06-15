package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.model.GalleryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryMapper {
    public static GalleryDTO toDTO(Gallery entity){
        GalleryDTO dto = new GalleryDTO();
        dto.setId(entity.getId());
        dto.setPath(entity.getLinkImage());
        dto.setType(entity.getType());
        return dto;
    }
    public static Gallery toEntity(GalleryDTO dto){
        Gallery entity = new Gallery();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setLinkImage(dto.getPath());
        return entity;
    }
    public static List<GalleryDTO> toDTOList(List<Gallery> galleries){
        return galleries.
                stream().
                map(GalleryMapper::toDTO)
                .collect(Collectors.toList());
    }
    public static List<Gallery> toEntityList(List<GalleryDTO> dtoList){
        return dtoList.
                stream().
                map(GalleryMapper::toEntity)
                .collect(Collectors.toList());
    }
}
