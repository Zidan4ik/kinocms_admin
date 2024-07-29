package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.Gallery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTOAdd {
    private Long id;
    private String titleFilmUkr;
    private String titleFilmEng;
    private String dateStart;
    private String dateEnd;
    private String descriptionFilmUkr;
    private String descriptionFilmEng;
    private String image;
    private String linkTrailer;
    private String urlCeo;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private List<MultipartFile> imagesMultipart;
    private Set<String> marks;
    private Set<String> genres;
    private MultipartFile fileImage;
    private String durationTime;
    private String year;
    private BigDecimal budget;
    private List<Gallery> galleries;
    private List<String> galleryDTO;
    public String getPathToImage(){
        return "/uploads/films/main-image/"+id+"/"+image;
    }
}
