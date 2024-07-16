package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Mark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDTOAdd {
    private Long id;
    private String number;
    private String nameSchema;
    private String nameBanner;
    private String urlCeo;
    private String descriptionUkr;
    private String descriptionEng;
    private List<Gallery> galleries;
    private List<Mark> marks;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
}
