package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
        return "/KinoCMSAdmin-R.Pravnyk/uploads/news/image/"+id+"/"+nameImage;
    }
}
