package com.example.kinocms_admin.entity.unifier;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.entity.PageTranslation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUnifier {
    private Page page;
    private List<Page> pages;
    private CeoBlock ceoBlockUkr;
    private CeoBlock ceoBlockEng;
    private PageTranslation pageTranslationUkr;
    private PageTranslation pageTranslationEng;
    private List<Gallery> galleries;


    public PageUnifier(Page page, PageTranslation pageTranslationUkr) {
        this.page = page;
        this.pageTranslationUkr = pageTranslationUkr;
    }

    public PageUnifier(Page page, CeoBlock ceoBlockUkr, CeoBlock ceoBlockEng) {
        this.page = page;
        this.ceoBlockUkr = ceoBlockUkr;
        this.ceoBlockEng = ceoBlockEng;
    }

    public PageUnifier(Page page, CeoBlock ceoBlockUkr, CeoBlock ceoBlockEng, PageTranslation pageTranslationUkr, PageTranslation pageTranslationEng) {
        this.page = page;
        this.ceoBlockUkr = ceoBlockUkr;
        this.ceoBlockEng = ceoBlockEng;
        this.pageTranslationUkr = pageTranslationUkr;
        this.pageTranslationEng = pageTranslationEng;
    }
}
