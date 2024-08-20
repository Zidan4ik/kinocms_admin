package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerImageDTO {
    private Long id;
    private String nameImage;
    private String text;
    private String url;
}
