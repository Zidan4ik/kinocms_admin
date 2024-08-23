package com.example.kinocms_admin.model;

import com.example.kinocms_admin.enums.BannerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerDTO {
    private Long id;
    private Integer rotationSpeed;
    private Boolean status;
    private BannerType type;
    private List<BannerImageDTO> banners;
}
