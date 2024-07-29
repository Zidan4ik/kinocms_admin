package com.example.kinocms_admin.entity.unifier;

import com.example.kinocms_admin.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUnifier {
    private New newEntity;
    private List<Gallery> galleryList;
    private Set<Mark> marks;
    private CeoBlock ceoBlockUkr;
    private CeoBlock ceoBlockEng;
    private PageTranslation pageTranslationUkr;
    private PageTranslation pageTranslationEng;

    public NewUnifier(New newEntity, PageTranslation pageTranslationUkr) {
        this.newEntity = newEntity;
        this.pageTranslationUkr = pageTranslationUkr;
    }

    public NewUnifier(New newEntity, CeoBlock ceoBlockUkr, CeoBlock ceoBlockEng, PageTranslation pageTranslationUkr, PageTranslation pageTranslationEng) {
        this.newEntity = newEntity;
        this.ceoBlockUkr = ceoBlockUkr;
        this.ceoBlockEng = ceoBlockEng;
        this.pageTranslationUkr = pageTranslationUkr;
        this.pageTranslationEng = pageTranslationEng;
    }
}
