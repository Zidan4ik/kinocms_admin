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
    private long id;
    private String path;
    private MultipartFile file;
    private GalleriesType type;
    private String link;
    public GalleryDTO(String path) {
        this.path = path;
    }

    public String createPathToImage(){
        return "./uploads/films/"+id+"/"+path;
    }
}
