package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTOAdd {
    private long id;
    private String title;
    private String description;
    private String image;
    private MultipartFile fileImage;
    private String linkTrailer;
    private List<GalleryDTO> galleries;
}
