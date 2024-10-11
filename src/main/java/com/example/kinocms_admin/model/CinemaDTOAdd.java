package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTOAdd {
    private Long id;
    private String titleUkr;
    private String titleEng;
    private String descriptionUkr;
    private String descriptionEng;
    private String conditionUkr;
    private String conditionEng;
    private String nameBanner;
    private String nameLogo;
    private MultipartFile fileBanner;
    private MultipartFile fileLogo;
    private String urlCeo;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private List<MultipartFile> imagesMultipart;
    private Set<String> marks;
    private List<Hall> hallDTOAddList;
    private List<Gallery> galleries;
    private List<String> galleryDTO;

    public String getPathToBanner() {
        return "/kinocms/uploads/cinemas/banner/" + id + "/" + nameBanner;
    }
    public String getPathToLogo() {
        return "/kinocms/uploads/cinemas/logo/" + id + "/" + nameLogo;
    }
}
