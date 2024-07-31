package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.PageType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTOAdd {
    private Long id;
    private PageType pageType;
    private LocalDate dateOfCreation;
    @Column(length = 2048)
    private String urlCeo;
    private Boolean status;
    private String titleUkr;
    private String titleEng;
    private String descriptionUkr;
    private String descriptionEng;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private String nameImage1;
    private String nameImage2;
    private String nameImage3;
    private String nameBanner;
    private MultipartFile fileImage1;
    private MultipartFile fileImage2;
    private MultipartFile fileImage3;
    private MultipartFile fileBanner;
    private List<Gallery> galleries;
    private List<String> galleriesDTO;
    private List<MultipartFile> filesGalleries;
}
