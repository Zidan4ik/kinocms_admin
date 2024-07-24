package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.Gallery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDTOAdd {
    private Long id;
    private Long cinemaId;
    private String number;
    private String nameSchema;
    private String nameBanner;
    private String urlCeo;
    private String descriptionUkr;
    private String descriptionEng;
    private List<Gallery> galleries;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private MultipartFile fileBanner;
    private MultipartFile fileSchema;
    private List<MultipartFile> imagesMultipart;
    private MultipartFile fileLogoCinema;
    private MultipartFile fileBannerCinema;

    public String getPathToSchema() {
        return "/uploads/hall/schema/" + id + "/" + nameSchema;
    }
    public String getPathToBanner() {
        return "/uploads/hall/banner/" + id + "/" + nameBanner;
    }
}
