package com.example.kinocms_admin.entity.unifier;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.entity.BannerImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BannerUnifier {
    private Banner banner;
    private List<BannerImage> bannerImages;

    public BannerUnifier(Banner banner, List<BannerImage> bannerImages) {
        this.banner = banner;
        this.bannerImages = bannerImages;
    }
}
