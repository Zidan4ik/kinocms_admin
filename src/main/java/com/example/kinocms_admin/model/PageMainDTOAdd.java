package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.PageType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageMainDTOAdd {
    private Long id;
    private PageType pageType;
    private LocalDate dateOfCreation;
    private Boolean status;
    private String phone1;
    private String phone2;
    private String urlCeo;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private String seoTextUkr;
    private String seoTextEng;
}
