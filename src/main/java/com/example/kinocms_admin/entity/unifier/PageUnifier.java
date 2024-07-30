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

    public PageUnifier(List<Page> pages) {
        this.pages = pages;
    }
}
