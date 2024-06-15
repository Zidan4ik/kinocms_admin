package com.example.kinocms_admin.entity.unifier;


import com.example.kinocms_admin.model.FilmDTOAdd;
import com.example.kinocms_admin.model.GalleryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilmUnifier {
    private FilmDTOAdd film;
    private List<GalleryDTO> galleries;
}
