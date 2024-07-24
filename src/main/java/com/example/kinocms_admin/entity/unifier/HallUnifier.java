package com.example.kinocms_admin.entity.unifier;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Hall;
import com.example.kinocms_admin.entity.PageTranslation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HallUnifier {
    private Hall hall;
    private PageTranslation pageTranslationUkr;
    private PageTranslation pageTranslationEng;
    private CeoBlock ceoBlockUkr;
    private CeoBlock ceoBlockEng;
    private List<Gallery> galleries;

    public HallUnifier(Hall hall, PageTranslation pageTranslationUkr,
                       PageTranslation pageTranslationEng, CeoBlock ceoBlockUkr, CeoBlock ceoBlockEng) {
        this.hall = hall;
        this.pageTranslationUkr = pageTranslationUkr;
        this.pageTranslationEng = pageTranslationEng;
        this.ceoBlockUkr = ceoBlockUkr;
        this.ceoBlockEng = ceoBlockEng;
    }
}
