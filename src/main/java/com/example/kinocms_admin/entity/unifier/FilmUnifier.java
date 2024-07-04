package com.example.kinocms_admin.entity.unifier;


import com.example.kinocms_admin.entity.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilmUnifier {
    private Film film;
    private Set<Mark> marks;
    private Set<Genre> genres;
    private List<Gallery> galleries;
    private CeoBlock ceoBlockEng;
    private CeoBlock ceoBlockUkr;
    private PageTranslation pageTranslationEng;
    private PageTranslation pageTranslationUkr;

    public FilmUnifier(Film film, CeoBlock ceoBlockEng, CeoBlock ceoBlockUkr,
                       PageTranslation pageTranslationEng, PageTranslation pageTranslationUkr) {
        this.film = film;
        this.ceoBlockEng = ceoBlockEng;
        this.ceoBlockUkr = ceoBlockUkr;
        this.pageTranslationEng = pageTranslationEng;
        this.pageTranslationUkr = pageTranslationUkr;
    }
}
