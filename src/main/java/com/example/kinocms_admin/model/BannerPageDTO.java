package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerPageDTO {
    private List<BannerDTO> bannersDTOS;
    private String pathToBackgroundImage;
    private List<MultipartFile> bannersMainF;
    private List<MultipartFile> bannersShareNewF;

    private List<String> jsonBannersImageMain;
    private List<String> jsonBannersImageShareNew;
    private BannerDTO bannerTest;

    public BannerPageDTO(List<BannerDTO> bannersDTOS, String pathToBackgroundImage) {
        this.bannersDTOS = bannersDTOS;
        this.pathToBackgroundImage = pathToBackgroundImage;
    }
}
