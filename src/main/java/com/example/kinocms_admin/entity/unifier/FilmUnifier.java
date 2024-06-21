package com.example.kinocms_admin.entity.unifier;


import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.model.FilmDTOAdd;
import com.example.kinocms_admin.model.GalleryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilmUnifier {
    private Film film;
    private Set<Mark> marks;
    private Set<Genre> genres;
    private List<Gallery> galleries;
    private CeoBlock ceoBlock;
}
