package com.example.kinocms_admin.model;

import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Mark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDtoAdd {
    private Long id;
    private String titleUkr;
    private String titleEng;
    private String dateOfPublication;
    private String descriptionUkr;
    private String descriptionEng;
    private String nameImage;
    private MultipartFile fileImage;
    private List<Gallery> galleriesBD;
    private List<MultipartFile> galleriesMF;
    private Set<String> marks;
    private String urlCeo;
    private String titleCeoUkr;
    private String titleCeoEng;
    private String keywordsCeoUkr;
    private String keywordsCeoEng;
    private String descriptionCeoUkr;
    private String descriptionCeoEng;
    private Boolean status;
    public String getPathToImage(){
        return "/uploads/news/image/"+id+"/"+nameImage;
    }
}
