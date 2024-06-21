package com.example.kinocms_admin.model;

import com.example.kinocms_admin.enums.GalleriesType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleryDTO {
    private Long id;
    private String name;
    private MultipartFile file;
    private GalleriesType type;
    private String link;
    public GalleryDTO(String name) {
        this.name = name;
    }

    public String pathToImage(){
        return "./uploads/films/"+id+"/"+name;
    }
}
