package com.example.kinocms_admin.mapper;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import com.example.kinocms_admin.entity.unifier.BannerUnifier;
import com.example.kinocms_admin.model.BannerDTO;
import com.example.kinocms_admin.model.BannerImageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BannerMapper {
    public static BannerDTO toDTOBanner(Banner entityBanner){
        BannerDTO dto = new BannerDTO();
        dto.setId(entityBanner.getId());
        dto.setStatus(entityBanner.isStatus());
        dto.setRotationSpeed(entityBanner.getRotationSpeed());
        dto.setType(entityBanner.getType());
        dto.setBanners(toDTOBannerImageList(entityBanner.getBannerImage()));
        return dto;
    }
    public static List<BannerDTO> toDTOBannersList(List<Banner> bannersComponents){
        return bannersComponents
                .stream()
                .map(BannerMapper::toDTOBanner)
                .collect(Collectors.toList());
    }
    private static BannerImageDTO toDTOBannerImage(BannerImage entity){
        BannerImageDTO dto = new BannerImageDTO();
        dto.setId(entity.getId());
        dto.setNameImage(entity.getNameImage());
        dto.setText(entity.getText());
        dto.setUrl(entity.getUrl());
        return dto;
    }
    private static List<BannerImageDTO> toDTOBannerImageList(List<BannerImage> bannerImages){
        return bannerImages
                .stream()
                .map(BannerMapper::toDTOBannerImage)
                .collect(Collectors.toList());
    }

    public static BannerUnifier toEntityBanner(BannerDTO dto){
        Banner banner = new Banner();
        banner.setId(dto.getId());
        banner.setRotationSpeed(dto.getRotationSpeed());
        banner.setStatus(dto.getStatus());
        banner.setType(dto.getType());

        List<BannerImage> bannerImages = new ArrayList<>();
        for (BannerImageDTO b:dto.getBanners()){
            bannerImages.add(new BannerImage(
                    b.getId(),
                    b.getNameImage(),
                    b.getText(),
                    b.getUrl(),
                    banner));
        }
        return new BannerUnifier(banner,bannerImages);
    }
}
