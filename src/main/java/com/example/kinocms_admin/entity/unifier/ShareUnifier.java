package com.example.kinocms_admin.entity.unifier;

import com.example.kinocms_admin.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ShareUnifier {
    private Share share;
    private Set<Mark> marks;
    private CeoBlock ceoBlockUkr;
    private CeoBlock ceoBlockEng;
    private PageTranslation pageTranslationUkr;
    private PageTranslation pageTranslationEng;

    public ShareUnifier(Share share, PageTranslation pageTranslationUkr) {
        this.share = share;
        this.pageTranslationUkr = pageTranslationUkr;
    }

    public ShareUnifier(Share share, CeoBlock ceoBlockUkr, CeoBlock ceoBlockEng, PageTranslation pageTranslationUkr, PageTranslation pageTranslationEng) {
        this.share = share;
        this.ceoBlockUkr = ceoBlockUkr;
        this.ceoBlockEng = ceoBlockEng;
        this.pageTranslationUkr = pageTranslationUkr;
        this.pageTranslationEng = pageTranslationEng;
    }
}
