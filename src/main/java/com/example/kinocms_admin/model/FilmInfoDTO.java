package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmInfoDTO {
    private Long id;
    private String title;
    private String pathImage;
    private String date;
    private String time;
    private String marks;
    private String genres;
    public String linkToImage(){
        return "/uploads/film/main-image/"+id+"/"+pathImage;
    }
}
