package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.model.GalleriesDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryMapper {
    public static GalleriesDTO toDTO(Gallery entity){
        GalleriesDTO dto = new GalleriesDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getLinkImage());
        dto.setType(entity.getType());
        return dto;
    }
    public static Gallery toEntity(GalleriesDTO dto){
        Gallery entity = new Gallery();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setLinkImage(dto.getName());
        return entity;
    }
    public static List<GalleriesDTO> toDTOList(List<Gallery> galleries){
        return galleries.
                stream().
                map(GalleryMapper::toDTO)
                .collect(Collectors.toList());
    }
    public static List<Gallery> toEntityList(List<GalleriesDTO> dtoList){
        return dtoList.
                stream().
                map(GalleryMapper::toEntity)
                .collect(Collectors.toList());
    }
}
