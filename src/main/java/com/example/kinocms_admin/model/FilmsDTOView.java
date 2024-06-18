package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmsDTOView {
    private long id;
    private String title;
    private String pathImage;
    private String date;
    public String linkToImage(){
        return "";
    }
}
