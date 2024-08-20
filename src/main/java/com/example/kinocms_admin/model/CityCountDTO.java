package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityCountDTO {
    private String city;
    private Long count;
}
