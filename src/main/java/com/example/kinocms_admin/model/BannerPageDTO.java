package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerPageDTO {
    private List<BannerDTO> bannersDTOS;
    private String pathToBackgroundImage;
}
