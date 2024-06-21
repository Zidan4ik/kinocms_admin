package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTOAdd {
    private Long id;
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
    private List<MultipartFile> imagesMultipart;
    private Set<String> marks;
    private Set<String> genres;
    private MultipartFile fileImage;
    private String durationTime;
    private String year;
    private BigDecimal budget;
}
