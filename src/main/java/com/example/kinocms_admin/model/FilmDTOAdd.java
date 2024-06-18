package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTOAdd {
    private long id;
    private String titleFilm;
    private String dateStart;
    private String dateEnd;
    private String descriptionFilm;
    private String image;
    private String linkTrailer;
    private String urlCeo;
    private String titleCeo;
    private String keywordsCeo;
    private String descriptionCeo;
    private List<GalleryDTO> images;
    private List<MultipartFile> imagesMultipart;
    private List<String> marks;
    private List<String> genres;
    private MultipartFile fileImage;
    private String durationTime;
    private String year;
    private BigDecimal budget;
}
