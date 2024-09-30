package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import com.example.kinocms_admin.entity.unifier.BannerUnifier;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.mapper.BannerMapper;
import com.example.kinocms_admin.model.BannerDTO;
import com.example.kinocms_admin.model.BannerPageDTO;
import com.example.kinocms_admin.service.BannerImageService;
import com.example.kinocms_admin.service.BannerService;
import com.example.kinocms_admin.service.serviceimp.ImageServiceImp;
import com.example.kinocms_admin.util.ImageUtil;
import com.example.kinocms_admin.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BannerController {
    private final BannerService bannerService;
    private final BannerImageService bannerImageService;
    private final ImageServiceImp imageServiceImp;
    @GetMapping("/banners")
    public String pageBanners() {
        return "banner/banners-view";
    }

    @GetMapping("/info-banners")
    @ResponseBody
    public BannerPageDTO getDataBanners() throws IOException {
        List<Banner> bannersComponents = bannerService.getAll();
        List<BannerDTO> dtoBannersComponents = BannerMapper.toDTOBannersList(bannersComponents);
        Path path1 = ImageUtil.getFileByPath(Path.of("./uploads/background/banner"));
        String pathToFile1 = path1.toString().substring(1);


        return new BannerPageDTO(dtoBannersComponents, pathToFile1);
    }

    @PostMapping("/banner-main/save")
    @ResponseBody
    public ResponseEntity<Object> saveDataBanners(@ModelAttribute BannerPageDTO bannerPageDTO) {
        List<BannerDTO> dtos = JsonUtil.transformationJsonToObject(bannerPageDTO.getJsonBannersImageMain(), BannerDTO.class);
        BannerUnifier entityBanner = new BannerUnifier();
        for (BannerDTO banner : dtos) {
            if (banner.getId() != null) {
                entityBanner = BannerMapper.toEntityBanner(banner);
                break;
            }
        }

        Optional<Banner> bannerById = bannerService.getById(entityBanner.getBanner().getId());
        if (bannerById.isPresent()) {
            List<BannerImage> bannerImages = bannerImageService.getAllByBanner(bannerById.get());
            for (BannerImage banner : bannerImages) {
                boolean existInNewContacts = entityBanner.getBannerImages().stream()
                        .filter(newBanner -> newBanner.getId() != null)
                        .anyMatch(newBanner -> newBanner.getId().equals(banner.getId()));
                if (!existInNewContacts) {
                    bannerImageService.delete(banner.getId());
                    imageServiceImp.deleteFiles(GalleriesType.banner, banner.getId());
                }
            }
        }

        bannerService.save(entityBanner.getBanner());
        bannerImageService.saveFiles(entityBanner.getBannerImages(), bannerPageDTO.getBannersMainF());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/banner-share-new/save")
    @ResponseBody
    public ResponseEntity<Object> saveDataBannersShareAndNew(@ModelAttribute BannerPageDTO bannerPageDTO) {
        List<BannerDTO> dtos = JsonUtil.transformationJsonToObject(bannerPageDTO.getJsonBannersImageShareNew(), BannerDTO.class);
        BannerUnifier entityBanner = new BannerUnifier();
        for (BannerDTO banner : dtos) {
            if (banner.getId() != null) {
                entityBanner = BannerMapper.toEntityBanner(banner);
                break;
            }
        }

        Optional<Banner> bannerById = bannerService.getById(entityBanner.getBanner().getId());
        if (bannerById.isPresent()) {
            List<BannerImage> bannerImages = bannerImageService.getAllByBanner(bannerById.get());
            for (BannerImage banner : bannerImages) {
                boolean existInNewContacts = entityBanner.getBannerImages().stream()
                        .filter(newBanner -> newBanner.getId() != null)
                        .anyMatch(newBanner -> newBanner.getId().equals(banner.getId()));
                if (!existInNewContacts) {
                    bannerImageService.delete(banner.getId());
                    imageServiceImp.deleteFiles(GalleriesType.banner, banner.getId());
                }
            }
        }

        bannerService.save(entityBanner.getBanner());
        bannerImageService.saveFiles(entityBanner.getBannerImages(), bannerPageDTO.getBannersShareNewF());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/image-background/save")
    @ResponseBody
    public ResponseEntity<Object> saveImageBackground(@RequestParam(name = "file") MultipartFile file) throws IOException {
        String fileName = imageServiceImp.generateFileName(file);
        imageServiceImp.saveFile(file,fileName,GalleriesType.background, ImageType.banner, 1L);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/image-background/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteImageBackground() {
        imageServiceImp.deleteFiles(GalleriesType.background,1L);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
