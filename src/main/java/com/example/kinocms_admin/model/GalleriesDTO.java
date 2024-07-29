package com.example.kinocms_admin.model;

import com.example.kinocms_admin.enums.GalleriesType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleriesDTO {
    private Long id;
    private String name;
    private GalleriesType type;
}
