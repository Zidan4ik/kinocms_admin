package com.example.kinocms_admin.entity.unifier;

import com.example.kinocms_admin.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaUnifier {
    private Cinema cinema;
    private List<Hall> halls;
    private CeoBlock ceoBlockUkr;
    private CeoBlock ceoBlockEng;
    private PageTranslation pageTranslationUkr;
    private PageTranslation pageTranslationEng;
    private Set<Mark> marks;
    private List<Gallery> galleries;
}
